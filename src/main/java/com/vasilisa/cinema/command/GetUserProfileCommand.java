package com.vasilisa.cinema.command;

import com.vasilisa.cinema.entity.Order;
import com.vasilisa.cinema.entity.User;
import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.OrderDao;
import com.vasilisa.cinema.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetUserProfileCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        int userId = ((User) session.getAttribute("loggedUser")).getId();
        String currentLocale = (String) session.getAttribute("locale");

        User user = new UserDao().get(userId);
        List<Order> orders = new OrderDao().getUserOrders(currentLocale, userId, "user");
        user.setOrders(orders);

        request.setAttribute("user", user);
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__PROFILE);
    }
}
