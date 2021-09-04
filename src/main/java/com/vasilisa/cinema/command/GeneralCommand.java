package com.vasilisa.cinema.command;

import bean.User;
import com.vasilisa.cinema.Path;
import database.FilmDao;
import database.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
        request.setAttribute("films", new FilmDao().getAll());
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__FILMS_LIST);
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
