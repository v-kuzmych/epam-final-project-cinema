package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.SeanceDao;
import com.vasilisa.cinema.entity.Seance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetAllSeancesCommand implements Command{

    private SeanceDao seanceDao = new SeanceDao();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        String locale = (String) session.getAttribute("locale");
        String dateFilter = request.getParameter("dateFilter");

        List<Seance> seances = seanceDao.getAll(locale, dateFilter, null);

        request.setAttribute("seances", seances);
        request.setAttribute("sitePage", "seances");
        request.setAttribute("dateFilter", dateFilter);

        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__SEANCES);
    }
}
