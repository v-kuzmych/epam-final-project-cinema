package com.vasilisa.cinema.command;

import bean.Film;
import bean.FilmDescription;
import bean.Seance;
import com.vasilisa.cinema.Path;
import database.FilmDao;
import database.FilmDescriptionDao;
import database.SeanceDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShowEditFilmPageCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int film_id = Integer.parseInt(request.getParameter("id"));
        Film film = new FilmDao().get(film_id);
        List<FilmDescription> descriptions = new FilmDescriptionDao().getByFilmId(film_id);

        HttpSession session = request.getSession(false);
        String currentLocale = (String) session.getAttribute("locale");
        String[] localeAttr = currentLocale.split("_");
        List<Seance> seances = new SeanceDao().getByFilmId(film_id, new Locale(localeAttr[0], localeAttr[1]));
        Map<String, List<Seance>> seancesMap = seances.stream().collect(Collectors.groupingBy(
                Seance::getFormatedDate,
                LinkedHashMap::new,
                Collectors.mapping(Function.identity(), Collectors.toList())
        ));

        request.setAttribute("film", film);
        request.setAttribute("descriptions", descriptions);
        request.setAttribute("seances", seancesMap);
        request.setAttribute("adminPage", "films");

        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__EDIT_FILM);
    }
}
