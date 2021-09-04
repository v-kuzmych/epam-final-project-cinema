package database;

import bean.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LanguageDao {

    private static final String GET_ALL_LANGUAGES = "SELECT * FROM language";

    public List<Language> getAll() throws ClassNotFoundException {
        List<Language> languagesList = new ArrayList<>();

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/cinema", "root", "password");

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection
                     .prepareStatement(GET_ALL_LANGUAGES)) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Language language = new Language();
                language.setId(rs.getInt(1));
                language.setShortName(rs.getString(3));
                language.setFullName(rs.getString(4));
                languagesList.add(language);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return languagesList;
    }

}
