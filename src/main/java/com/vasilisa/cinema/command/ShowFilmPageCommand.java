package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.FilmDao;
import com.vasilisa.cinema.entity.Film;
import com.vasilisa.cinema.entity.Seance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Get film and his grouped seances by date
 */

public class ShowFilmPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowFilmPageCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        HttpSession session = request.getSession(false);
        int film_id = Integer.parseInt(request.getParameter("id"));
        String locale = (String) session.getAttribute("locale");
        String dateFilter = request.getParameter("dateFilter");

        // get film and his grouped seances by date
        Film film = new FilmDao().get(locale, film_id);
        Map<Film, Map<String, List<Seance>>> mappedSeances = new GetScheduleCommand().getMappedSeances(locale, dateFilter, film);

        request.setAttribute("film", film);
        request.setAttribute("schedule", mappedSeances);
        request.setAttribute("dateFilter", dateFilter);

        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__FILM);
    }
}
