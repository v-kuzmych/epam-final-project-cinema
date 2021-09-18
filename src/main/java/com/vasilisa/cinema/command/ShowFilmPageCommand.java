package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import database.FilmDao;
import entity.Film;
import entity.Seance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public class ShowFilmPageCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        int film_id = Integer.parseInt(request.getParameter("id"));
        String locale = (String) session.getAttribute("locale");
        String dateFilter = request.getParameter("dateFilter");

        Film film = new FilmDao().get(locale, film_id);
        Map<Film, Map<String, List<Seance>>> mappedSeances = new GetScheduleCommand().getMappedSeances(locale, dateFilter, film);

        request.setAttribute("film", film);
        request.setAttribute("schedule", mappedSeances);
        request.setAttribute("dateFilter", dateFilter);

        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__FILM);
    }
}
