package database;

import entity.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LanguageDao {

    private static final String GET_ALL_LANGUAGES = "SELECT * FROM language";

    public List<Language> getAll() {
        List<Language> languagesList = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_LANGUAGES);

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Language language = new Language();
                language.setId(rs.getInt(1));
                language.setShortName(rs.getString(3));
                language.setFullName(rs.getString(4));
                languagesList.add(language);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
        return languagesList;
    }

}
