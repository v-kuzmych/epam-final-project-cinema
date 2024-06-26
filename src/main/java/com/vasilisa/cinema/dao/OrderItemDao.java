package com.vasilisa.cinema.dao;

import com.vasilisa.cinema.util.DBManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDao {

    private static final String GET_ORDER_ITEMS_BY_SEANCE = "SELECT oi.`row_number`, oi.seat_number " +
                                                        "FROM `order` o " +
                                                        "JOIN order_item oi ON oi.order_id = o.id " +
                                                        "WHERE o.seance_id = ?";
    private static final String INSERT_ORDER_ITEM = "INSERT INTO order_item (order_id, `row_number`, seat_number) VALUES (?, ?, ?)";

    private static final Logger logger = LogManager.getLogger(OrderItemDao.class);

    public List<String> getOccupiedSeatsAtSeance(int seanceId) {
        List<String> occupiedSeats = new ArrayList<String>();

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_ORDER_ITEMS_BY_SEANCE);
            preparedStatement.setInt(1, seanceId);

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                occupiedSeats.add(rs.getInt(1) + "_" + rs.getInt(2));
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("getOccupiedSeatsAtSeance failed with error " + ex.getMessage());
        } finally {
            DBManager.getInstance().close(connection);
        }

        logger.debug("Get occupied seats list");
        return occupiedSeats;
    }

    public boolean createItems(Connection connection, int orderId, String[] places) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_ORDER_ITEM);

            for (String place : places) {
                String[] selectedPlace = place.split("_");
                int row = Integer.parseInt(selectedPlace[0]);
                int seat = Integer.parseInt(selectedPlace[1]);

                preparedStatement.setInt(1, orderId);
                preparedStatement.setInt(2, row);
                preparedStatement.setInt(3, seat);
                preparedStatement.addBatch();
            }

            int[] items = preparedStatement.executeBatch();
            for (int item : items) {
                if (item != 1) {
                    logger.error("createItems failed");
                    return false;
                }
            }

            preparedStatement.close();
            logger.debug("Items created");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("createItems failed with error " + ex.getMessage());
        }

        return false;
    }
}
