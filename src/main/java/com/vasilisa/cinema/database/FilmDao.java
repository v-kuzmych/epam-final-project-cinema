package com.vasilisa.cinema.database;

import com.vasilisa.cinema.entity.Film;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        } finally {
            DBManager.getInstance().close(connection);
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
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().close(connection);
        }

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
        } finally {
            DBManager.getInstance().close(connection);
        }
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
        } finally {
            DBManager.getInstance().close(connection);
        }

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
                status = new FilmDescriptionDao().create(connection, create(connection, film));
            } else {
                // update film
                if (update(connection, film)) {
                    status = new FilmDescriptionDao().update(connection, film);
                }
            }
            connection.commit();

        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

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
                }
            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
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
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
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
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}