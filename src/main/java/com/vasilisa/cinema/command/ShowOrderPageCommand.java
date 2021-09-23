package com.vasilisa.cinema.command;

import com.vasilisa.cinema.entity.Seance;
import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.OrderItemDao;
import com.vasilisa.cinema.dao.SeanceDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowOrderPageCommand implements Command{

    private SeanceDao seanceDao = new SeanceDao();
    private OrderItemDao orderItemDao = new OrderItemDao();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int seanceId = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession(false);
        String locale = (String) session.getAttribute("locale");

        List<String> occupiedSeats = orderItemDao.getOccupiedSeatsAtSeance(seanceId);
        Seance seance = seanceDao.get(seanceId, locale);

        request.setAttribute("seance", seance);
        request.setAttribute("occupiedSeats", occupiedSeats);
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ORDER);
    }
}
