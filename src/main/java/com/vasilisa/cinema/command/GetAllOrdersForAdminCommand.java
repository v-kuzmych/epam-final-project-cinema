package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.OrderDao;
import com.vasilisa.cinema.dao.SeanceDao;
import com.vasilisa.cinema.entity.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

/**
 *  Get all users orders for admin
 */

public class GetAllOrdersForAdminCommand implements Command {

    private static final Logger logger = LogManager.getLogger(GetAllOrdersForAdminCommand.class);

    private OrderDao orderDao = new OrderDao();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        HttpSession session = request.getSession(false);
        String currentLocale = (String) session.getAttribute("locale");
        List<Order> orders = orderDao.getAll(currentLocale);

        request.setAttribute("orders", orders);
        request.setAttribute("adminPage", "orders");

        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ORDERS_LIST);
    }
}
