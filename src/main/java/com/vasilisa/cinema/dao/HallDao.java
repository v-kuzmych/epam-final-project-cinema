package com.vasilisa.cinema.dao;

import com.vasilisa.cinema.entity.Hall;
import com.vasilisa.cinema.util.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HallDao {
    private static final String GET_HALL_BY_ID = "SELECT * FROM hall WHERE id = ?";

    public Hall get(int hallId) {
        Hall hall = new Hall();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_HALL_BY_ID);
            preparedStatement.setInt(1, hallId);

            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                hall.setId(rs.getInt(1));
                hall.setNumberOfRows(rs.getInt(2));
                hall.setNumberOfSeats(rs.getInt(3));
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().close(connection);
        }

        return hall;
    }
}
