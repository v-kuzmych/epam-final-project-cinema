package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import database.SeanceDao;
import entity.Seance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetAllSeancesCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        String locale = (String) session.getAttribute("locale");
        String dateFilter = request.getParameter("dateFilter");

        List<Seance> seances = new SeanceDao().getAll(locale, dateFilter);

        request.setAttribute("seances", seances);
        request.setAttribute("sitePage", "seances");
        request.setAttribute("dateFilter", dateFilter);

        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__SEANCES);
    }
}
