package database;

import bean.Film;
import bean.FilmDescription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDao {

    private static final String GET_ALL_FILMS_FOR_ADMIN = "SELECT f.*," +
                                                            "group_concat(l.id) as locales," +
                                                            "group_concat(fd.name) as names, " +
                                                            "group_concat(fd.description) as descriptions " +
                                                            "FROM film f " +
                                                            "LEFT JOIN film_description fd ON fd.film_id = f.id " +
                                                            "LEFT JOIN language l ON l.Id = fd.language_id " +
                                                            "GROUP BY f.id";
    private static final String GET_FILM_BY_ID = "SELECT * FROM film f WHERE f.id = ?";
    private static final String INSERT_FILM = "INSERT INTO film (img) VALUES  (?)";
    private static final String INSERT_FILM_DESCRIPTION = "INSERT INTO film_description (film_id, language_id, name, description) VALUES  (?, ?, ?, ?)";

    public List<Film> getAll() throws ClassNotFoundException {
        List<Film> filmsList = new ArrayList<>();

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/cinema", "root", "password");

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection
                     .prepareStatement(GET_ALL_FILMS_FOR_ADMIN)) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Film film = new Film();
                List<FilmDescription> filmDesc = new ArrayList<>();

                film.setId(rs.getInt(1));
                film.setImg(rs.getString(2));

                String[] locales = (rs.getString(3)).split(",", -1);
                String[] names = (rs.getString(4)).split(",", -1);
                String[] descriptions = (rs.getString(5)).split(",", -1);

                for (int i = 0; i < locales.length; i++) {
                    filmDesc.add(new FilmDescription(Integer.parseInt(locales[i]), names[i], descriptions[i]));
                }

                film.setFilmDescriptions(filmDesc);
                filmsList.add(film);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return filmsList;
    }

    public Film get(int id) throws ClassNotFoundException {
        Film film = null;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/cinema", "root", "password");

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection
                     .prepareStatement(GET_FILM_BY_ID)) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                film = new Film();
                film.setId(rs.getInt(1));
                film.setImg(rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return film;
    }

    public Film create(Film film) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/cinema", "root", "password");

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection
                     .prepareStatement(INSERT_FILM, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, film.getImg());

            if (preparedStatement.executeUpdate() > 0) {
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        film.setId(rs.getInt(1));
                        createFilmDescFromFilm(film);
                    }
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private void createFilmDescFromFilm(Film film) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/cinema", "root", "password");

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection
                     .prepareStatement(INSERT_FILM_DESCRIPTION)) {

            for (FilmDescription fd : film.getFilmDescriptions()) {
                preparedStatement.setInt(1, film.getId());
                preparedStatement.setInt(2, fd.getLanguageId());
                preparedStatement.setString(3, fd.getName());
                preparedStatement.setString(4, fd.getDescription());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}