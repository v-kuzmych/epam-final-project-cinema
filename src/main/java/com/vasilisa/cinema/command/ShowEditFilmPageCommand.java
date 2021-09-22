package com.vasilisa.cinema.command;

import com.vasilisa.cinema.entity.Film;
import com.vasilisa.cinema.entity.FilmDescription;
import com.vasilisa.cinema.entity.Seance;
import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.database.FilmDao;
import com.vasilisa.cinema.database.FilmDescriptionDao;
import com.vasilisa.cinema.database.SeanceDao;

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
        HttpSession session = request.getSession(false);
        String currentLocale = (String) session.getAttribute("locale");
        String[] localeAttr = currentLocale.split("_");
        int film_id = Integer.parseInt(request.getParameter("id"));

        Film film = new FilmDao().get(currentLocale, film_id);
        List<FilmDescription> descriptions = new FilmDescriptionDao().getByFilmId(film_id);


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

        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__EDIT_FILM);
    }
}
