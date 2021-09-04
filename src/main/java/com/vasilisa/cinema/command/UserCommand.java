package com.vasilisa.cinema.command;

import bean.User;
import com.vasilisa.cinema.Path;
import database.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserCommand {

    public CommandResult login(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        // error handler
        String errorMessage = null;
        String page = Path.PAGE__ERROR_PAGE;

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
//            log.error("errorMessage --> " + errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, page);
        }

        User loggedUser = new UserDao().login(user);
//        log.trace("Found in DB: user --> " + user);

        if (loggedUser == null) {
            errorMessage = "Cannot find user with such login/password";
            request.setAttribute("errorMessage", errorMessage);
//            log.error("errorMessage --> " + errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, page);
        } else {
            String userRole = loggedUser.getRole();
//            log.trace("userRole --> " + userRole);

            if ("admin".equals(userRole))
                page = Path.PAGE__ADMIN_DASHBOARD;

            if ("user".equals(userRole))
                page = Path.PAGE__PROFILE;

            session.setAttribute("loggedUser", loggedUser);
//            log.trace("Set the session attribute: user --> " + user);

            session.setAttribute("userRole", userRole);
//            log.trace("Set the session attribute: userRole --> " + userRole);

//            log.info("User " + user + " logged as " + userRole.toString().toLowerCase());


            if (session.getAttribute("locale") == null ) {
                session.setAttribute("locale", "uk_UA");
            }
        }

//        log.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
    }

    public CommandResult logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("loggedUser");
            session.invalidate();
        }

        return new CommandResult(CommandResult.ResponseType.REDIRECT, Path.PAGE__LOGIN);
    }
}
