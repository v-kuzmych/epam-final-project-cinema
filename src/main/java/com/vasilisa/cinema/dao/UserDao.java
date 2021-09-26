package com.vasilisa.cinema.dao;

import com.vasilisa.cinema.entity.*;
import com.vasilisa.cinema.util.DBManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static final String GET_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM user WHERE email = ? AND password = ?";
    private static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String INSERT_USER = "INSERT INTO user (name, email, password, date) VALUES  (?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE user SET name = ?, email = ?, password = ? WHERE id = ?";
    private static final String GET_ALL_USERS = "SELECT * FROM user";

    private static final Logger logger = LogManager.getLogger(UserDao.class);

    public User create(User user) {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setDate(4, (Date) new java.util.Date());

            if (preparedStatement.executeUpdate() > 0) {
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                    logger.debug("Created user");
                    return user;
                }
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Create user failed with error " + ex.getMessage());
        } finally {
            DBManager.getInstance().close(connection);
        }

        return null;
    }

    public boolean update(User user) {
        boolean result = false;

        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getId());

            if (preparedStatement.executeUpdate() > 0) {
                result = true;
                logger.debug("Updated user");
            }

            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Update user failed with error " + ex.getMessage());
        } finally {
            DBManager.getInstance().close(connection);
        }

        return result;
    }

    public User login(User user) {
        User loggedUser = null;

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL_AND_PASSWORD);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());

            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                loggedUser = new User();
                loggedUser.setId(rs.getInt(1));
                loggedUser.setName(rs.getString(2));
                loggedUser.setEmail(rs.getString(3));
                loggedUser.setRole(rs.getString(5));

                logger.debug("Logged user");
            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Login user failed with error " + ex.getMessage());
        } finally {
            DBManager.getInstance().close(connection);
        }

        return loggedUser;
    }

    public List<User> getAll() {
        List<User> usersList = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_USERS);

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setRole(rs.getString(5));
                user.setDate(rs.getDate(6));
                usersList.add(user);
            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Get all users failed with error " + ex.getMessage());
        } finally {
            DBManager.getInstance().close(connection);
        }

        logger.debug("Get users list");
        return usersList;
    }

    public User get(int id) {
        User user = null;

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_USER_BY_ID);
            preparedStatement.setInt(1, id);

            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setPassword(rs.getString(4));
                user.setRole(rs.getString(5));
            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Get user failed with error " + ex.getMessage());
        } finally {
            DBManager.getInstance().close(connection);
        }

        logger.debug("Get user with id " + user.getId());
        return user;
    }

}
