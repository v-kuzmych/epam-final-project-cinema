package com.vasilisa.cinema.command;

import com.vasilisa.cinema.entity.Seance;
import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.OrderItemDao;
import com.vasilisa.cinema.dao.SeanceDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Show order page for reserve tickets by user
 */

public class ShowOrderPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowOrderPageCommand.class);

    private SeanceDao seanceDao = new SeanceDao();
    private OrderItemDao orderItemDao = new OrderItemDao();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        int seanceId = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession(false);
        String locale = (String) session.getAttribute("locale");

        List<String> occupiedSeats = orderItemDao.getOccupiedSeatsAtSeance(seanceId);
        Seance seance = seanceDao.get(seanceId, locale);

        request.setAttribute("seance", seance);
        request.setAttribute("occupiedSeats", occupiedSeats);

        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ORDER);
    }
}
