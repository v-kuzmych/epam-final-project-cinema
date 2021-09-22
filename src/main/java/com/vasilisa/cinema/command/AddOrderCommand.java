package com.vasilisa.cinema.command;

import com.vasilisa.cinema.entity.Order;
import com.vasilisa.cinema.entity.User;
import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.OrderDao;
import com.vasilisa.cinema.dao.SeanceDao;

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
        String errorPage = Path.PAGE__ERROR_PAGE;

        if (user == null) {
            return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__LOGIN);
        }

        String[] places = request.getParameterValues("places");
        if (places.length == 0) {
            errorMessage = "Please, select seat";
            request.setAttribute("errorMessage", errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, errorPage);
        }

        int seanceId = Integer.parseInt(request.getParameter("seance_id"));

        boolean checkForEmptySeats = new OrderDao().checkForEmptySeats(seanceId, places);
        if (!checkForEmptySeats) {
            errorMessage = "Sorry, seats are not available, please select others";
            request.setAttribute("errorMessage", errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, errorPage);
        }

        int seancePrice = new SeanceDao().getPrice(seanceId);
        Order order = new Order();
        order.setUserId(user.getId());
        order.setSeanceId(seanceId);
        order.setPrice(seancePrice * places.length);
        order.setDate(LocalDateTime.now());

        if (!new OrderDao().create(order, places)) {
            errorMessage = "Не вдалося забронювати замовлення";
            request.setAttribute("errorMessage", errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, errorPage);
        };

        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__SUCCESS_ORDER);
    }
}
