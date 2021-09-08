package com.vasilisa.cinema.command;

import bean.Order;
import bean.Seance;
import com.vasilisa.cinema.Path;
import database.OrderDao;
import database.SeanceDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowOrderPageCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int seanceId = Integer.parseInt(request.getParameter("id"));
        List<Order> orders = new OrderDao().getOrdersBySeance(seanceId);

        HttpSession session = request.getSession(false);
        String locale = (String) session.getAttribute("locale");
        Seance seance = new SeanceDao().get(seanceId, locale);

        request.setAttribute("seance", seance);
        request.setAttribute("order", orders);
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ORDER);
    }
}
