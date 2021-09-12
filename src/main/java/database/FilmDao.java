package database;

import bean.Film;
import bean.FilmDescription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDao {

    private static final String GET_ALL_FILMS = "SELECT f.*, fd.name, fd.description, f.img " +
                                                            "FROM film f " +
                                                            "LEFT JOIN language l ON l.locale = ? " +
                                                            "LEFT JOIN film_description fd ON fd.film_id = f.id AND fd.language_id = l.Id " +
                                                            "ORDER BY fd.name ASC " +
                                                            "LIMIT ? OFFSET ?";
    private static final String GET_FILMS_COUNT = "SELECT count(*) FROM film";

    private static final String GET_FILM_BY_ID = "SELECT * FROM film f WHERE f.id = ?";
    private static final String INSERT_FILM = "INSERT INTO film (img, duration) VALUES  (?, ?)";
    private static final String INSERT_FILM_DESCRIPTION = "INSERT INTO film_description (film_id, language_id, name, description) VALUES  (?, ?, ?, ?)";

    public List<Film> getAll(String locale, int limit, int offset) {

        List<Film> filmsList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_FILMS);
            preparedStatement.setString(1, locale);
            preparedStatement.setInt(2, limit);
            preparedStatement.setInt(3, offset);

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Film film = new Film();

                film.setId(rs.getInt(1));
                film.setImg(rs.getString(2));
                film.setDuration(rs.getInt(3));
                film.setName(rs.getString(4));
                film.setDescription(rs.getString(5));

                filmsList.add(film);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }

        return filmsList;
    }

    public int getFilmsCount() {
        int count = 0;

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_FILMS_COUNT);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }

        return count;
    }

    public Film get(int id) {
        Film film = null;

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_FILM_BY_ID);
            preparedStatement.setInt(1, id);

            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                film = new Film();
                film.setId(rs.getInt(1));
                film.setImg(rs.getString(2));
                film.setDuration(rs.getInt(3));
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
        return film;
    }

    public Film create(Film film) {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(INSERT_FILM, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, film.getImg());
            preparedStatement.setInt(2, film.getDuration());

            if (preparedStatement.executeUpdate() > 0) {
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    film.setId(rs.getInt(1));
                    return film;
                }
            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
        return null;
    }

    public void createFilmDescFromFilm(Film film) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(INSERT_FILM_DESCRIPTION);

            for (FilmDescription fd : film.getFilmDescriptions()) {
                preparedStatement.setInt(1, film.getId());
                preparedStatement.setInt(2, fd.getLanguageId());
                preparedStatement.setString(3, fd.getName());
                preparedStatement.setString(4, fd.getDescription());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
    }

}