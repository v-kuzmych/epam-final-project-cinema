package com.vasilisa.cinema.dao;

import com.vasilisa.cinema.entity.Film;
import com.vasilisa.cinema.entity.FilmDescription;
import com.vasilisa.cinema.util.DBManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDescriptionDao {

    private static final String GET_FILM_DESCRIPTIONS = "SELECT l.id, fd.name, fd.description FROM language l " +
                                                        "LEFT JOIN film_description fd ON fd.language_id = l.id AND fd.film_id = ?";

    private static final String INSERT_FILM_DESCRIPTION = "INSERT INTO film_description (film_id, language_id, name, description) VALUES  (?, ?, ?, ?)";
    private static final String UPDATE_FILM_DESCRIPTION = "UPDATE film_description SET name = ?, description = ? WHERE  film_id = ? AND  language_id = ?";

    private static final Logger logger = LogManager.getLogger(FilmDescriptionDao.class);

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
            logger.error("getByFilmId failed with error " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().close(connection);
        }

        logger.debug("Get film description");
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
            logger.debug("Created film description");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("create failed with error " + ex.getMessage());
        }

        return false;
    }

    public boolean update(Connection connection, Film film) {
        logger.debug("Updating film description....");
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
            logger.debug("Updated film description");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("update failed with error " + ex.getMessage());
        }

        return false;
    }
}
