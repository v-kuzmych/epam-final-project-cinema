package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.database.OrderDao;
import com.vasilisa.cinema.entity.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetAllOrdersForAdminCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        String currentLocale = (String) session.getAttribute("locale");
        List<Order> orders = new OrderDao().getAll(currentLocale);

        request.setAttribute("orders", orders);
        request.setAttribute("adminPage", "orders");
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ORDERS_LIST);
    }
}
