package com.vasilisa.cinema.command;

import com.vasilisa.cinema.entity.User;
import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.UserDao;
import com.vasilisa.cinema.util.SecurePassword;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Login command.
 */

public class LoginCommand implements Command {

    private static final Logger logger = LogManager.getLogger(LoginCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        HttpSession session = request.getSession();
        // obtain login and password from the request
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // error handler
        String errorMessage = null;
        String page = Path.PAGE__ERROR_PAGE;
        request.setAttribute("prevPage", Path.PAGE__LOGIN);

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            logger.error("errorMessage --> " + errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, page);
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(new SecurePassword().md5Hex(password));
        User loggedUser = new UserDao().login(user);
        logger.trace("Found in DB: user --> " + user);

        if (loggedUser == null) {
            errorMessage = "Cannot find user with such login/password";
            request.setAttribute("errorMessage", errorMessage);
            logger.error("errorMessage --> " + errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, page);
        } else {
            String userRole = loggedUser.getRole();
            logger.trace("userRole --> " + userRole);

            if ("admin".equals(userRole))
                page = Path.COMMAND_SHOW_DASHBOARD;

            if ("user".equals(userRole))
                page = Path.COMMAND_SHOW_PROFILE;

            session.setAttribute("loggedUser", loggedUser);
            logger.trace("Set the session attribute: user --> " + user);

            session.setAttribute("userRole", userRole);
            logger.trace("Set the session attribute: userRole --> " + userRole);

            logger.info("User " + user + " logged as " + userRole);
        }

        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
    }
}
