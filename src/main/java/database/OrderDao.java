package database;
import bean.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderDao {

    private static final String GET_ORDER_BY_SELECTED_SEATS = "SELECT o.id " +
                                                                "FROM `order` o " +
                                                                "JOIN order_item oi ON oi.order_id = o.id " +
                                                                "WHERE o.seance_id = ? AND oi.`row_number` = ? AND oi.seat_number = ?";

    private static final String INSERT_ORDER = "INSERT INTO `order` (user_id, seance_id, price, date) VALUES  (?, ?, ?, ?)";
    private static final String GET_USER_ORDERS = "SELECT o.id as order_id, o.date as order_date, o.price, " +
                                                    "GROUP_CONCAT(oi.`row_number`) as ordered_rows, GROUP_CONCAT(oi.seat_number) as ordered_seats, " +
                                                    "f.id as film_id, f.img, fd.name as film_name, f.duration, s.date as seance_date " +
                                                    "FROM user u " +
                                                    "JOIN `order` o ON o.user_id = u.id " +
                                                    "JOIN order_item oi ON oi.order_id = o.id " +
                                                    "JOIN seance s ON s.id = o.seance_id " +
                                                    "JOIN film f ON f.id = s.film_id " +
                                                    "LEFT JOIN language l ON l.locale = ? " +
                                                    "LEFT JOIN film_description fd ON fd.film_id = f.id AND fd.language_id = l.Id " +
                                                    "WHERE u.id = ? ";

    private static final String FUTURE_SEANSES_FOR_USER = "AND s.date > CURDATE() GROUP BY oi.order_id ORDER BY s.date";
    private static final String HISTORY_ORDERS_FOR_ADMIN = "GROUP BY oi.order_id ORDER BY o.date";

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

    public List<Order> getUserOrders(String locale, int id, String role) {
        List<Order> ordersList = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            String query = GET_USER_ORDERS + FUTURE_SEANSES_FOR_USER;
            if ("admin".equals(role)) {
                query = GET_USER_ORDERS + HISTORY_ORDERS_FOR_ADMIN;
            }

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, locale);
            preparedStatement.setInt(2, id);

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                List<OrderItem> orderItems = new ArrayList<>();
                order.setId(rs.getInt(1));
                order.setDate(LocalDateTime.parse(rs.getString(2), fmt));
                order.setPrice(rs.getInt(3));

                String[] rows = (rs.getString(4)).split(",", -1);
                String[] seats = (rs.getString(5)).split(",", -1);

                for (int i = 0; i < rows.length; i++) {
                    orderItems.add(new OrderItem(Integer.parseInt(rows[i]), Integer.parseInt(seats[i])));
                }
                order.setOrderItems(orderItems);

                Film film = new Film();
                film.setId(rs.getInt(6));
                film.setImg(rs.getString(7));
                film.setName(rs.getString(8));
                film.setDuration(rs.getInt(9));

                Seance seance = new Seance();
                seance.setFilm(film);
                seance.setDate(LocalDateTime.parse(rs.getString(10), fmt));
                String[] localeAttr = locale.split("_");
                seance.setFormatedDate(new Locale(localeAttr[0], localeAttr[1]));

                order.setSeance(seance);
                ordersList.add(order);
            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(connection);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(connection);
        }

        return ordersList;
    }

}
