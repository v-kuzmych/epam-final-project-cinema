package com.vasilisa.cinema.command;

import bean.Order;
import bean.User;
import com.vasilisa.cinema.Path;
import database.OrderDao;
import database.SeanceDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

public class AddOrderCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("loggedUser");

        // error handler
        String errorMessage = null;
        String page = Path.PAGE__ERROR_PAGE;

        if (user == null) {
            errorMessage = "Unregister user cannot by ticket";
            request.setAttribute("errorMessage", errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, page);
        }

        String[] places = request.getParameterValues("places");
        if (places.length == 0) {
            errorMessage = "Please, select seat";
            request.setAttribute("errorMessage", errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, page);
        }

        int seanceId = Integer.parseInt(request.getParameter("seance_id"));

        boolean checkForEmptySeats = new OrderDao().checkForEmptySeats(seanceId, places);
        if (!checkForEmptySeats) {
            errorMessage = "Sorry, seats are not available, please select others";
            request.setAttribute("errorMessage", errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, page);
        }

        int seancePrice = new SeanceDao().getPrice(seanceId);
        Order order = new Order();
        order.setUserId(user.getId());
        order.setSeanceId(seanceId);
        order.setPrice(seancePrice);
        order.setDate(LocalDateTime.now());

        new OrderDao().create(order, places);

        return null;
    }
}
