package database;

import bean.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    private static final String GET_ALL_FILMS_FOR_USER = "SELECT f.*, l.id as lang_id, fd.name, fd.description, f.img " +
                                                            "FROM film f " +
                                                            "LEFT JOIN language l ON l.locale = ? " +
                                                            "LEFT JOIN film_description fd ON fd.film_id = f.id AND fd.language_id = l.Id " +
                                                            "ORDER BY fd.name ASC";

    public List<Order> getOrdersBySeance(int seance) {
        List<Order> ordersList = new ArrayList<>();


        return ordersList;
    }
}
