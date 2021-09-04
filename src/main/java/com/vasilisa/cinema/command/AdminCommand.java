package com.vasilisa.cinema.command;

import bean.*;
import com.vasilisa.cinema.Path;
import database.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AdminCommand {

    public CommandResult getAllFilms(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("films", new FilmDao().getAll());
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ADMIN_FILMS_LIST);
    }

    public CommandResult showAddFilmPage(HttpServletRequest request, HttpServletResponse response) {
        List<Language> languages = new LanguageDao().getAll();
        request.setAttribute("languages", languages);
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ADD_FILM);
    }

    public CommandResult showEditFilmPage(HttpServletRequest request, HttpServletResponse response) {
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

        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__EDIT_FILM);
    }

//    public String deleteFilm(HttpServletRequest request, HttpServletResponse response) {
//        Film film = new Film();
////        film.deleteById(request.getParameter("id"));
//    }

    public CommandResult saveFilm(HttpServletRequest request, HttpServletResponse response) {
        Film film = new Film();
        film.setImg("image_placeholder");

        String langIds[] = request.getParameter("lang_ids").split(",");

        List<FilmDescription> fd = new ArrayList<>();

        for (String landId : langIds){
            fd.add(new FilmDescription(
                    Integer.parseInt(landId),
                    request.getParameter("name." + landId),
                    request.getParameter("desc." + landId)
            ));
        }

        film.setFilmDescriptions(fd);

        film = new FilmDao().create(film);
        String page = "/controller?command=film_edit&id=" + film.getId();
        return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
    }

    public CommandResult addSeance(HttpServletRequest request, HttpServletResponse response) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("date") + " " + request.getParameter("time"), fmt);

        int filmId = Integer.parseInt(request.getParameter("id"));
        Seance seance = new Seance();
        seance.setFilmId(filmId);
        seance.setDate(dateTime);
        new SeanceDao().create(seance);

        String page = "/controller?command=film_edit&tab=schedule&id=" + filmId;
        return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
    }

    public CommandResult getSchedule(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        String locale = (String) session.getAttribute("locale");

        List<Seance> seances = new SeanceDao().getAll(locale);

        Map<Film, List<Seance>> scheduleMap = seances.stream().collect(Collectors.groupingBy(
                Seance::getFilm,
                LinkedHashMap::new,
                Collectors.mapping(Function.identity(), Collectors.toList())
        ));

        Map<Film, Map<String, List<Seance>>> dashboard = new LinkedHashMap<>();

        for (Map.Entry<Film, List<Seance>> entry : scheduleMap.entrySet()) {
            Film film = entry.getKey();
            Map<String, List<Seance>> groupedSeances = entry.getValue().stream().collect(Collectors.groupingBy(
                    Seance::getFormatedDate,
                    LinkedHashMap::new,
                    Collectors.mapping(Function.identity(), Collectors.toList())
            ));
            dashboard.put(film, groupedSeances);
        }
        request.setAttribute("schedule", dashboard);

        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ADMIN_SCHEDULE);
    }

    public CommandResult getAllUsers(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("users", new UserDao().getAll());
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__USERS_LIST);
    }

    public CommandResult getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        User user = new UserDao().get(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("user", user);
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__USER_INFO);
    }
}
