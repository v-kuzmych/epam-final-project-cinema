package com.vasilisa.cinema.command;

import com.vasilisa.cinema.entity.Order;
import com.vasilisa.cinema.entity.User;
import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.OrderDao;
import com.vasilisa.cinema.dao.SeanceDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 *  Booking tickets by user and redirect on success order page
 */

public class AddOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AddOrderCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("loggedUser");

        // error handler
        String errorMessage = null;
        String errorPage = Path.PAGE__ERROR_PAGE;

        if (user == null) {
            logger.debug("User dont logged");
            return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__LOGIN);
        }

        String[] places = request.getParameterValues("places");

        // check selected places
        if (places.length == 0) {
            errorMessage = "Please, select seat";
            request.setAttribute("errorMessage", errorMessage);
            logger.error("User dont select seat");
            return new CommandResult(CommandResult.ResponseType.FORWARD, errorPage);
        }

        int seanceId = Integer.parseInt(request.getParameter("seance_id"));

        // check empty seats
        boolean checkForEmptySeats = new OrderDao().checkForEmptySeats(seanceId, places);
        if (!checkForEmptySeats) {
            errorMessage = "Sorry, seats are not available, please select others";
            request.setAttribute("errorMessage", errorMessage);
            logger.error("Seats are not available");
            return new CommandResult(CommandResult.ResponseType.FORWARD, errorPage);
        }

        int seancePrice = new SeanceDao().getPrice(seanceId);
        Order order = new Order();
        order.setUserId(user.getId());
        order.setSeanceId(seanceId);
        order.setPrice(seancePrice * places.length);
        order.setDate(LocalDateTime.now());

        // create order
        if (!new OrderDao().create(order, places)) {
            errorMessage = "Failed to book order";
            request.setAttribute("errorMessage", errorMessage);
            logger.error(errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, errorPage);
        };

        logger.debug("The order was successfully created");
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__SUCCESS_ORDER);
    }
}
