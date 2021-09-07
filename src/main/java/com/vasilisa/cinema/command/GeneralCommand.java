package com.vasilisa.cinema.command;

import bean.Film;
import bean.Order;
import bean.Seance;
import bean.User;
import com.vasilisa.cinema.Path;
import database.FilmDao;
import database.OrderDao;
import database.SeanceDao;
import database.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GeneralCommand {

    public CommandResult switchLanguage(HttpServletRequest request, HttpServletResponse response) {
        String newLocale = request.getParameter("param");
        HttpSession session = request.getSession(false);
        String currentLocale = (String) session.getAttribute("locale");

        if (!newLocale.equals(currentLocale)) {
            if ("en".equals(newLocale)) {
                session.setAttribute("locale", "en_US");
            } else {
                session.setAttribute("locale", "uk_UA");
            }
        }

        return new CommandResult(CommandResult.ResponseType.REDIRECT, request.getHeader("Referer"));
    }

    public CommandResult register(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        // error handler
        String errorMessage = null;
        String forward = Path.PAGE__ERROR_PAGE;

        User newUser = new UserDao().create(user);
        HttpSession session = request.getSession();
        if (newUser != null) {
            session.setAttribute("loggedUser", newUser);
            forward = Path.PAGE__PROFILE;
        } else {
            errorMessage = "Cannot create user with such login/password";
            request.setAttribute("errorMessage", errorMessage);
//            log.error("errorMessage --> " + errorMessage);
            new CommandResult(CommandResult.ResponseType.FORWARD, forward);
        }

        return new CommandResult(CommandResult.ResponseType.FORWARD, forward);
    }

    public CommandResult getAllFilms(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        String currentLocale = (String) session.getAttribute("locale");
        request.setAttribute("films", new FilmDao().getAllForUser(currentLocale));
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__FILMS_LIST);
    }

    public CommandResult showOrderPage(HttpServletRequest request, HttpServletResponse response) {
        int seanceId = Integer.parseInt(request.getParameter("id"));
        List<Order> orders = new OrderDao().getOrdersBySeance(seanceId);

        HttpSession session = request.getSession(false);
        String locale = (String) session.getAttribute("locale");
        Seance seance = new SeanceDao().get(seanceId, locale);

        request.setAttribute("seance", seance);
        request.setAttribute("order", orders);
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ORDER);
    }

    public CommandResult addOrder(HttpServletRequest request, HttpServletResponse response) {

        String[] places = request.getParameterValues("places");

        return null;
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

        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__SCHEDULE);
    }

    public CommandResult noCommand(HttpServletRequest request, HttpServletResponse response) {
//        log.debug("Command starts");
//
        String errorMessage = "No such command";
        request.setAttribute("errorMessage", errorMessage);
//        log.error("Set the request attribute: errorMessage --> " + errorMessage);
//        log.debug("Command finished");

        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ERROR_PAGE);
    }
}
