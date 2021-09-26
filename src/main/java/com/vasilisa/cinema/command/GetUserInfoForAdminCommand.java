package com.vasilisa.cinema.command;

import com.vasilisa.cinema.entity.Order;
import com.vasilisa.cinema.entity.User;
import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.OrderDao;
import com.vasilisa.cinema.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *  Get user and his order history for admin
 */

public class GetUserInfoForAdminCommand implements Command {

    private static final Logger logger = LogManager.getLogger(GetUserInfoForAdminCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        HttpSession session = request.getSession(false);
        String currentLocale = (String) session.getAttribute("locale");
        int userId = Integer.parseInt(request.getParameter("id"));

        User user = new UserDao().get(userId);
        List<Order> orders = new OrderDao().getUserOrders(currentLocale, userId, "admin");
        user.setOrders(orders);

        request.setAttribute("user", user);
        request.setAttribute("adminPage", "users");

        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__USER_INFO);
    }
}
