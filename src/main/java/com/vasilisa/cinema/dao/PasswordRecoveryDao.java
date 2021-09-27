package com.vasilisa.cinema.dao;

import com.vasilisa.cinema.entity.PasswordRecovery;
import com.vasilisa.cinema.util.DBManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class PasswordRecoveryDao {

    private static final String GET_TOKEN = "SELECT * FROM password_recovery WHERE token = ? AND expiration_date > NOW()";
    private static final String INSERT_TOKEN = "INSERT INTO password_recovery (user_id, token, expiration_date) VALUES  (?, ?, now() + interval 10 minute)";

    private static final Logger logger = LogManager.getLogger(PasswordRecoveryDao.class);

    public PasswordRecovery get(String token) {
        PasswordRecovery pr = null;

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_TOKEN);
            preparedStatement.setString(1, token);

            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                logger.debug("Get token");
                pr = new PasswordRecovery();
                pr.setId(rs.getInt(1));
                pr.setUserId(rs.getInt(2));
                pr.setToken(rs.getString(3));
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Get failed with error " + ex.getMessage());
        } finally {
            DBManager.getInstance().close(connection);
        }

        return pr;
    }

    public boolean create(PasswordRecovery pr) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(INSERT_TOKEN, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, pr.getUserId());
            preparedStatement.setString(2, pr.getToken());

            // the token was successfully created
            if (preparedStatement.executeUpdate() > 0) {
                logger.debug("the token was successfully created");
                return true;
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("create failed with error " + ex.getMessage());
        } finally {
            DBManager.getInstance().close(connection);
        }

        logger.debug("create token was failed");
        return false;
    }
}
