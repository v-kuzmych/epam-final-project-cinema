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

    public List<FilmDescription> getByFilmId(int id) {
        List<FilmDescription> filmDescriptions = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_FILM_DESCRIPTIONS);
            preparedStatement.setInt(1, id);

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                filmDescriptions.add(new FilmDescription(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
                rs.close();
                preparedStatement.close();
            } catch (SQLException ex) {
                DBManager.getInstance().rollbackAndClose(connection);
                ex.printStackTrace();
            } finally {
                DBManager.getInstance().commitAndClose(connection);
            }

        return filmDescriptions;
    }

    public void createFromFilm(Film film) {
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
