package database;

import bean.Film;
import bean.FilmDescription;
import bean.Seance;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SeanceDao {

    private static final String INSERT_SEANCE = "INSERT INTO seance (film_id, date, price) VALUES  (?, ?, ?)";
    private static final String GET_SEANCE_BY_FILM_ID = "SELECT * FROM seance WHERE film_id = ? ORDER BY date ASC ";
    private static final String GET_ALL_SEANCES = "SELECT s.*, l.id as lang_id, fd.name, fd.description, f.img " +
                                                    "FROM seance s " +
                                                    "JOIN film f ON f.Id = s.film_id " +
                                                    "LEFT JOIN language l ON l.locale = ? " +
                                                    "LEFT JOIN film_description fd ON fd.film_id = f.id AND fd.language_id = l.Id " +
                                                    "ORDER BY s.date ASC";
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");

    public void create(Seance seance) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(INSERT_SEANCE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, seance.getFilmId());
            preparedStatement.setString(2, fmt.format(seance.getDate()));
            preparedStatement.setInt(3, 111);

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

    public List<Seance> getByFilmId(int id, Locale currentLocale) throws ClassNotFoundException {
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
                // 4 price
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

    public List<Seance> getAll(String locale) {
        List<Seance> seancesList = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_SEANCES);
            preparedStatement.setString(1, locale);

            String[] localeAttr = locale.split("_");
            Locale currentLocale = new Locale(localeAttr[0], localeAttr[1]);

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Film film = new Film();
                List<FilmDescription> filmDescriptions = new ArrayList<>();

                film.setId(rs.getInt(2));
               // price rs.getString(4)
                filmDescriptions.add(new FilmDescription(rs.getInt(5), rs.getString(6), rs.getString(7)));
                film.setFilmDescriptions(filmDescriptions);
                film.setImg(rs.getString(8));

                Seance seance = new Seance();
                seance.setId(rs.getInt(1));
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
}
