package com.vasilisa.cinema.dao;

import com.vasilisa.cinema.entity.Film;
import com.vasilisa.cinema.util.DBManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Film entity
 */

public class FilmDao {

    private static final String GET_ALL_FILMS = "SELECT f.*, fd.name, fd.description " +
                                                            "FROM film f " +
                                                            "LEFT JOIN language l ON l.locale = ? " +
                                                            "LEFT JOIN film_description fd ON fd.film_id = f.id AND fd.language_id = l.Id " +
                                                            "ORDER BY fd.name ASC " +
                                                            "LIMIT ? OFFSET ?";
    private static final String GET_FILMS_COUNT = "SELECT count(*) FROM film";

    private static final String GET_FILM_BY_ID = "SELECT f.*, fd.name, fd.description " +
                                                    "FROM film f " +
                                                    "LEFT JOIN language l ON l.locale = ? " +
                                                    "LEFT JOIN film_description fd ON fd.film_id = f.id AND fd.language_id = l.Id " +
                                                    "WHERE f.id = ?";
    private static final String GET_FILM_DURATION_BY_ID = "SELECT f.duration WHERE f.id = ?";
    private static final String INSERT_FILM = "INSERT INTO film (img, duration) VALUES  (?, ?)";
    private static final String UPDATE_FILM = "UPDATE film SET img = ?, duration = ? WHERE id = ? ";
    private static final String DELETE_FILM = "DELETE FROM film WHERE id = ?";

    private static final Logger logger = LogManager.getLogger(FilmDao.class);

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
            ex.printStackTrace();
            logger.error("Get all films failed with error " + ex.getMessage());
        } finally {
            DBManager.getInstance().close(connection);
        }

        logger.debug("Get films list");
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
            ex.printStackTrace();
            logger.error("Get films count failed with error " + ex.getMessage());
        } finally {
            DBManager.getInstance().close(connection);
        }

        logger.debug("Get films count");
        return count;
    }

    public Film get(String locale, int id) {
        Film film = null;

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_FILM_BY_ID);
            preparedStatement.setString(1, locale);
            preparedStatement.setInt(2, id);

            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                film = new Film();
                film.setId(rs.getInt(1));
                film.setImg(rs.getString(2));
                film.setDuration(rs.getInt(3));
                film.setName(rs.getString(4));
                film.setDescription(rs.getString(5));
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Get film failed with error " + ex.getMessage());
        } finally {
            DBManager.getInstance().close(connection);
        }

        logger.debug("Get film");
        return film;
    }

    public int getDuration(int filmId) {
        int id = 0;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_FILM_DURATION_BY_ID);
            preparedStatement.setInt(1, filmId);

            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Get duration failed with error " + ex.getMessage());
        } finally {
            DBManager.getInstance().close(connection);
        }

        logger.debug("Get film duration");
        return id;
    }

    public boolean save(Film film) {
        boolean status = false;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();

            connection.setAutoCommit(false);
            // if film doesn't exist - create new film with description
            if (film.getId() == 0) {
                logger.debug("Creating film...");
                status = new FilmDescriptionDao().create(connection, create(connection, film));
            } else {
                // update film
                logger.debug("Updating film...");
                if (update(connection, film)) {
                    status = new FilmDescriptionDao().update(connection, film);
                }
            }
            connection.commit();

        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
            logger.error("Save film failed with error " + ex.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        logger.debug("Saving film " + status);
        return status;
    }

    public Film create(Connection connection, Film film) {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_FILM, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, film.getImg());
            preparedStatement.setInt(2, film.getDuration());

            if (preparedStatement.executeUpdate() > 0) {
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    film.setId(rs.getInt(1));
                    logger.debug("Created film");
                }
            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Create film failed with error " + ex.getMessage());
        }

        return film;
    }

    public boolean update(Connection connection, Film film) {
        boolean status = false;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_FILM);
            preparedStatement.setString(1, film.getImg());
            preparedStatement.setInt(2, film.getDuration());
            preparedStatement.setInt(3, film.getId());

            if (preparedStatement.executeUpdate() > 0) {
                status = true;
                logger.debug("Updated film");
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Update failed with error " + ex.getMessage());
        }

        return status;
    }

    public boolean delete(int filmId) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(DELETE_FILM);
            preparedStatement.setInt(1, filmId);

            if (preparedStatement.executeUpdate() == 1) {
                logger.debug("Deleted film");
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Delete film failed with error " + ex.getMessage());
        }

        return false;
    }
}