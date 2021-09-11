package database;
import bean.Order;

import java.sql.*;
import java.time.format.DateTimeFormatter;

public class OrderDao {

    private static final String GET_ORDER_BY_SELECTED_SEATS = "SELECT o.id " +
                                                                "FROM `order` o " +
                                                                "JOIN order_item oi ON oi.order_id = o.id " +
                                                                "WHERE o.seance_id = ? AND oi.`row_number` = ? AND oi.seat_number = ?";

    private static final String INSERT_ORDER = "INSERT INTO `order` (user_id, seance_id, price, date) VALUES  (?, ?, ?, ?)";

    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");

    public boolean checkForEmptySeats(int seanceId, String[] places) {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();

            for (int i = 0; i < places.length; i++) {
                String[] selectedPlace = places[i].split("_");
                int row = Integer.parseInt(selectedPlace[0]);
                int seat = Integer.parseInt(selectedPlace[1]);

                preparedStatement = connection.prepareStatement(GET_ORDER_BY_SELECTED_SEATS);
                preparedStatement.setInt(1, seanceId);
                preparedStatement.setInt(2, row);
                preparedStatement.setInt(3, seat);

                rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    return false;
                }
                rs.close();
                preparedStatement.close();
            }
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }

        return true;
    }

    public void create(Order order, String[] places) {
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);

            int orderId = createOrder(order);
            new OrderItemDao().createItems(orderId, places);

        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
        }
    }

    public int createOrder(Order order) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = DBManager.getInstance().getConnection();

            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setInt(2, order.getSeanceId());
            preparedStatement.setInt(3, order.getPrice());
            preparedStatement.setString(4, fmt.format(order.getDate()));

            if (preparedStatement.executeUpdate() > 0) {
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    order.setId(rs.getInt(1));
                }
            }

            preparedStatement.close();

        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }

        return order.getId();
    }
}
