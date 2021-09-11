package database;

import bean.Film;
import bean.FilmDescription;
import bean.Hall;
import bean.Seance;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SeanceDao {

    private static final String INSERT_SEANCE = "INSERT INTO seance (film_id, date, price, hall_id) VALUES  (?, ?, ?, ?)";
    private static final String GET_SEANCE_BY_FILM_ID = "SELECT * FROM seance WHERE film_id = ? ORDER BY date ASC ";
    private static final String GET_ALL_SEANCES = "SELECT s.*, l.id as lang_id, fd.name, fd.description, f.img " +
                                                    "FROM seance s " +
                                                    "JOIN film f ON f.id = s.film_id " +
                                                    "LEFT JOIN language l ON l.locale = ? " +
                                                    "LEFT JOIN film_description fd ON fd.film_id = f.id AND fd.language_id = l.Id ";
    private static final String WHERE_SEANCE_DATE_IS_TODAY = "WHERE s.date > CURDATE() AND s.date < CURDATE() + interval 1 day ";
    private static final String WHERE_SEANCE_DATE_IS_TOMORROW = "WHERE s.date > CURDATE() + interval 1 day AND s.date < CURDATE() + interval 2 day ";
    private static final String WHERE_SEANCE_DATE_IS_WEEK = "WHERE s.date > CURDATE() AND s.date < CURDATE() + interval 1 week ";
    private static final String WHERE_SEANCE_DATE_IS_MONTH = "WHERE s.date > CURDATE() AND s.date < CURDATE() + interval 1 month ";
    private static final String ORDER_SEANCE_BY_DATE = "ORDER BY s.date ASC";
    private static final String GET_SEANCE_BY_ID = "SELECT s.*, h.number_of_rows, h.number_of_seats, fd.language_id, fd.name, fd.description, f.img " +
                                                    "FROM seance s " +
                                                    "JOIN film f ON f.id = s.film_id " +
                                                    "LEFT JOIN language l ON l.locale = ? " +
                                                    "LEFT JOIN film_description fd ON fd.film_id = f.id AND fd.language_id = l.Id " +
                                                    "JOIN hall h ON h.id = s.hall_id " +
                                                    "WHERE s.id = ? ";
    private static final String GET_SEANCE_PRICE_BY_ID = "SELECT s.price FROM seance s WHERE s.id = ?";

    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");

    public void create(Seance seance) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(INSERT_SEANCE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, seance.getFilmId());
            preparedStatement.setString(2, fmt.format(seance.getDate()));
            preparedStatement.setInt(3, seance.getPrice());
            preparedStatement.setInt(4, 1);

            if (preparedStatement.executeUpdate() <= 0) {
                // error
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
    }

    public List<Seance> getByFilmId(int id, Locale currentLocale) {
        List<Seance> seancesList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_SEANCE_BY_FILM_ID);
            preparedStatement.setInt(1, id);

//            String[] localeAttr = locale.split("_");
//            Locale currentLocale = new Locale(localeAttr[0], localeAttr[1]);

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Seance seance = new Seance();
                seance.setId(rs.getInt(1));
                seance.setDate(LocalDateTime.parse(rs.getString(3), fmt));
                seance.setFormatedDate(currentLocale);
                seance.setFilmId(rs.getInt(2));
                seance.setPrice(rs.getInt(5));
                seancesList.add(seance);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
        return seancesList;
    }

    public List<Seance> getAll(String locale, String dateFilter) {
        List<Seance> seancesList = new ArrayList<>();
        String dateFilterQuery = switchDateFilterQuery(dateFilter);

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_SEANCES + dateFilterQuery + ORDER_SEANCE_BY_DATE);
            preparedStatement.setString(1, locale);

            String[] localeAttr = locale.split("_");
            Locale currentLocale = new Locale(localeAttr[0], localeAttr[1]);

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Film film = new Film();
                List<FilmDescription> filmDescriptions = new ArrayList<>();

                film.setId(rs.getInt(2));
                filmDescriptions.add(new FilmDescription(rs.getInt(6), rs.getString(7), rs.getString(8)));
                film.setFilmDescriptions(filmDescriptions);
                film.setImg(rs.getString(9));

                Seance seance = new Seance();
                seance.setId(rs.getInt(1));
                seance.setPrice(rs.getInt(4));
                seance.setDate(LocalDateTime.parse(rs.getString(3), fmt));
                seance.setFormatedDate(currentLocale);
                seance.setFilmId(rs.getInt(2));
                seance.setFilm(film);

                seancesList.add(seance);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }

        return seancesList;
    }

    public Seance get(int seanceId, String locale) {
        Seance seance = new Seance();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_SEANCE_BY_ID);
            preparedStatement.setString(1, locale);
            preparedStatement.setInt(2, seanceId);

            String[] localeAttr = locale.split("_");
            Locale currentLocale = new Locale(localeAttr[0], localeAttr[1]);

            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Hall hall = new Hall();
                Film film = new Film();
                List<FilmDescription> filmDescriptions = new ArrayList<>();

                seance.setId(rs.getInt(1));
                seance.setDate(LocalDateTime.parse(rs.getString(3), fmt));
                seance.setFormatedDate(currentLocale);
                seance.setFilmId(rs.getInt(2));
                seance.setPrice(rs.getInt(5));

                hall.setId(rs.getInt(4));
                hall.setNumberOfRows(rs.getInt(6));
                hall.setNumberOfSeats(rs.getInt(7));

                film.setId(rs.getInt(2));
                filmDescriptions.add(new FilmDescription(rs.getInt(8), rs.getString(9), rs.getString(10)));
                film.setFilmDescriptions(filmDescriptions);
                film.setImg(rs.getString(11));

                seance.setFilm(film);
                seance.setHall(hall);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }

        return seance;
    }

    public int getPrice(int seanceId) {
        Seance seance = new Seance();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_SEANCE_PRICE_BY_ID);
            preparedStatement.setInt(1, seanceId);

            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                seance.setPrice(rs.getInt(1));
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }

        return seance.getPrice();
    }

    public String switchDateFilterQuery(String dateFilter) {
        String result = "";

        if ("tomorrow".equals(dateFilter)) {
            result = WHERE_SEANCE_DATE_IS_TOMORROW;
        } else if ("week".equals(dateFilter)) {
            result = WHERE_SEANCE_DATE_IS_WEEK;
        } else if ("month".equals(dateFilter)) {
            result = WHERE_SEANCE_DATE_IS_MONTH;
        } else {
            result = WHERE_SEANCE_DATE_IS_TODAY;
        }

        return result;
    }
}
