package com.vasilisa.cinema.command;

import bean.User;
import com.vasilisa.cinema.Path;
import database.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegisterCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
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
}
