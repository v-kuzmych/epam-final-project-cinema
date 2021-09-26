package com.vasilisa.cinema.dao;

import com.vasilisa.cinema.entity.Language;
import com.vasilisa.cinema.util.DBManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LanguageDao {

    private static final String GET_ALL_LANGUAGES = "SELECT * FROM language";

    private static final Logger logger = LogManager.getLogger(LanguageDao.class);

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
            ex.printStackTrace();
            logger.error("Get all failed with error " + ex.getMessage());
        } finally {
            DBManager.getInstance().close(connection);
        }

        logger.debug("Get language list");
        return languagesList;
    }

}
