package database;

import entity.Film;
import entity.Hall;
import entity.Seance;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SeanceDao {

    private static final String INSERT_SEANCE = "INSERT INTO seance (film_id, date, price, hall_id, free_seats) VALUES  (?, ?, ?, ?, ?)";
    private static final String GET_SEANCE_BY_FILM_ID = "SELECT * FROM seance WHERE film_id = ? ORDER BY date ASC ";
    private static final String GET_ALL_SEANCES = "SELECT s.*, fd.name, fd.description, f.img, f.duration " +
                                                    "FROM seance s " +
                                                    "JOIN film f ON f.id = s.film_id " +
                                                    "LEFT JOIN language l ON l.locale = ? " +
                                                    "LEFT JOIN film_description fd ON fd.film_id = f.id AND fd.language_id = l.Id ";
    private static final String WHERE_SEANCE_DATE_IS_TODAY = "WHERE s.date > NOW() AND s.date < NOW() + interval 1 day ";
    private static final String WHERE_SEANCE_DATE_IS_TOMORROW = "WHERE s.date > NOW() + interval 1 day AND s.date < NOW() + interval 2 day ";
    private static final String WHERE_SEANCE_DATE_IS_WEEK = "WHERE s.date > NOW() AND s.date < NOW() + interval 1 week ";
    private static final String WHERE_SEANCE_DATE_IS_MONTH = "WHERE s.date > NOW() AND s.date < NOW() + interval 1 month ";
    private static final String ORDER_SEANCE_BY_DATE = "ORDER BY s.date ASC";
    private static final String GET_SEANCE_BY_ID = "SELECT s.*, h.number_of_rows, h.number_of_seats, fd.name, f.img, f.duration " +
                                                    "FROM seance s " +
                                                    "JOIN film f ON f.id = s.film_id " +
                                                    "LEFT JOIN language l ON l.locale = ? " +
                                                    "LEFT JOIN film_description fd ON fd.film_id = f.id AND fd.language_id = l.Id " +
                                                    "JOIN hall h ON h.id = s.hall_id " +
                                                    "WHERE s.id = ? ";
    private static final String GET_SEANCE_PRICE_BY_ID = "SELECT s.price FROM seance s WHERE s.id = ?";
    private static final String DELETE_SEANCE = "DELETE FROM seance WHERE id = ?";


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
            preparedStatement.setInt(4, seance.getHall().getId());
            preparedStatement.setInt(5, seance.getFreeSeats());

            if (preparedStatement.executeUpdate() <= 0) {
                // error
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().close(connection);
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
                seance.setFormattedDate(currentLocale);
                seance.setFilmId(rs.getInt(2));
                seance.setPrice(rs.getInt(5));
                seance.setFreeSeats(rs.getInt(6));
                seancesList.add(seance);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().close(connection);
        }
        return seancesList;
    }

    public List<Seance> getAll(String locale, String dateFilter) {
        List<Seance> seancesList = new ArrayList<>();
        String dateFilterQuery = getDateFilterQuery(dateFilter);

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
                Seance seance = new Seance();
                Film film = new Film();
                film.setId(rs.getInt(2));
                film.setName(rs.getString(7));
                film.setDescription(rs.getString(8));
                film.setImg(rs.getString(9));
                film.setDuration(rs.getInt(10));
                seance.setFilm(film);

                seance.setId(rs.getInt(1));
                seance.setPrice(rs.getInt(5));
                seance.setFreeSeats(rs.getInt(6));
                seance.setDate(LocalDateTime.parse(rs.getString(3), fmt));
                seance.setFormattedDate(currentLocale);
                seance.setFilmId(rs.getInt(2));

                seancesList.add(seance);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().close(connection);
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
                hall.setId(rs.getInt(4));
                hall.setNumberOfRows(rs.getInt(7));
                hall.setNumberOfSeats(rs.getInt(8));
                seance.setHall(hall);

                Film film = new Film();
                film.setId(rs.getInt(2));
                film.setName(rs.getString(9));
                film.setImg(rs.getString(10));
                film.setDuration(rs.getInt(11));
                seance.setFilm(film);

                seance.setId(rs.getInt(1));
                seance.setDate(LocalDateTime.parse(rs.getString(3), fmt));
                seance.setFormattedDate(currentLocale);
                seance.setFilmId(rs.getInt(2));
                seance.setPrice(rs.getInt(5));
                seance.setFreeSeats(rs.getInt(6));
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().close(connection);
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
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().close(connection);
        }

        return seance.getPrice();
    }

    public String getDateFilterQuery(String dateFilter) {
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

    public boolean delete(int seanceId) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(DELETE_SEANCE);
            preparedStatement.setInt(1, seanceId);

            if (preparedStatement.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
