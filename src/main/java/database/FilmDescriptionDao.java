package database;

import entity.Film;
import entity.FilmDescription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDescriptionDao {

    private static final String GET_FILM_DESCRIPTIONS = "SELECT l.id, fd.name, fd.description FROM language l " +
                                                        "LEFT JOIN film_description fd ON fd.language_id = l.id AND fd.film_id = ?";

    private static final String INSERT_FILM_DESCRIPTION = "INSERT INTO film_description (film_id, language_id, name, description) VALUES  (?, ?, ?, ?)";
    private static final String UPDATE_FILM_DESCRIPTION = "UPDATE film_description SET name = ?, description = ? WHERE  film_id = ? AND  language_id = ?";
    DBManager dbManager = DBManager.getInstance();

    public List<FilmDescription> getByFilmId(int id) {
        List<FilmDescription> filmDescriptions = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(GET_FILM_DESCRIPTIONS);
            preparedStatement.setInt(1, id);

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                filmDescriptions.add(new FilmDescription(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
                rs.close();
                preparedStatement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DBManager.getInstance().close(connection);
            }

        return filmDescriptions;
    }

    public boolean create(Connection connection, Film film) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_FILM_DESCRIPTION);

            for (FilmDescription fd : film.getFilmDescriptions()) {
                preparedStatement.setInt(1, film.getId());
                preparedStatement.setInt(2, fd.getLanguageId());
                preparedStatement.setString(3, fd.getName());
                preparedStatement.setString(4, fd.getDescription());
                preparedStatement.addBatch();
            }

            int[] items = preparedStatement.executeBatch();
            for (int item : items) {
                if (item != 1) {
                    return false;
                }
            }

            preparedStatement.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean update(Connection connection, Film film) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_FILM_DESCRIPTION);

            for (FilmDescription fd : film.getFilmDescriptions()) {
                preparedStatement.setString(1, fd.getName());
                preparedStatement.setString(2, fd.getDescription());
                preparedStatement.setInt(3, film.getId());
                preparedStatement.setInt(4, fd.getLanguageId());
                preparedStatement.addBatch();
            }

            int[] items = preparedStatement.executeBatch();
            for (int item : items) {
                if (item != 1) {
                    return false;
                }
            }

            preparedStatement.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
