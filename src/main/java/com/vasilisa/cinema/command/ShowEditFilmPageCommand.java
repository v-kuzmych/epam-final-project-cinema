package com.vasilisa.cinema.command;

import com.vasilisa.cinema.entity.Film;
import com.vasilisa.cinema.entity.FilmDescription;
import com.vasilisa.cinema.entity.Seance;
import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.FilmDao;
import com.vasilisa.cinema.dao.FilmDescriptionDao;
import com.vasilisa.cinema.dao.SeanceDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Get film and show edit film page for admin
 */

public class ShowEditFilmPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowEditFilmPageCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        HttpSession session = request.getSession(false);
        String currentLocale = (String) session.getAttribute("locale");
        String[] localeAttr = currentLocale.split("_");
        int film_id = Integer.parseInt(request.getParameter("id"));

        // get film and all his descriptions
        Film film = new FilmDao().get(currentLocale, film_id);
        List<FilmDescription> descriptions = new FilmDescriptionDao().getByFilmId(film_id);

        // group film seances by date
        List<Seance> seances = new SeanceDao().getByFilmId(film_id, new Locale(localeAttr[0], localeAttr[1]));
        Map<String, List<Seance>> seancesMap = seances.stream().collect(Collectors.groupingBy(
                Seance::getFormattedDate,
                LinkedHashMap::new,
                Collectors.mapping(Function.identity(), Collectors.toList())
        ));

        request.setAttribute("film", film);
        request.setAttribute("descriptions", descriptions);
        request.setAttribute("seances", seancesMap);
        request.setAttribute("adminPage", "films");

        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__EDIT_FILM);
    }
}
