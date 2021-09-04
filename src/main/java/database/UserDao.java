package database;

import bean.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static final String GET_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM user WHERE email = ? AND password = ?";
    private static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String INSERT_USER = "INSERT INTO user (email, password) VALUES  (?, ?)";
    private static final String GET_ALL_USERS = "SELECT * FROM user";

    public User create(User user) throws ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());

            if (preparedStatement.executeUpdate() > 0) {
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                    return user;
                }
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }

        return null;
    }

    public User login(User user) throws ClassNotFoundException {
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
            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
        return loggedUser;
    }

    public List<User> getAll() throws ClassNotFoundException {
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
                usersList.add(user);
            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
        return usersList;
    }

    public User get(int id) throws ClassNotFoundException {
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
                user.setRole(rs.getString(5));
            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }
        return user;
    }

}
