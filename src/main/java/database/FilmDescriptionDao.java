package database;

import bean.FilmDescription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDescriptionDao {

    private static final String GET_FILM_DESCRIPTIONS = "SELECT l.id, fd.name, fd.description FROM language l " +
                                                        "LEFT JOIN film_description fd ON fd.language_id = l.id AND fd.film_id = ?";

    public List<FilmDescription> getByFilmId(int id) throws ClassNotFoundException {
        List<FilmDescription> filmDescriptions = new ArrayList<>();

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/cinema", "root", "password");

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection
                     .prepareStatement(GET_FILM_DESCRIPTIONS)) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                filmDescriptions.add(new FilmDescription(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return filmDescriptions;
    }
}
