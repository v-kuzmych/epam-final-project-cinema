package com.vasilisa.cinema.command;

import com.vasilisa.cinema.entity.User;
import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.database.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.vasilisa.cinema.util.SecurePassword;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegisterCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // error handler
        String errorMessage = null;
        String forward = Path.PAGE__ERROR_PAGE;

        if (email.length() == 0 || password.length() == 0) {
            logger.error("Failed to create new user");

            errorMessage = "Sorry, failed register";
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("prevPage", Path.PAGE__REGISTER);
            return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ERROR_PAGE);
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(new SecurePassword().md5Hex(password));

        User newUser = new UserDao().create(user);
        HttpSession session = request.getSession();
        if (newUser != null) {
            logger.info("The user was successfully created");

            session.setAttribute("loggedUser", newUser);
            forward = Path.COMMAND_SHOW_PROFILE;
        } else {
            errorMessage = "Cannot create user with such login/password";
            request.setAttribute("errorMessage", errorMessage);
            logger.error(errorMessage);
            new CommandResult(CommandResult.ResponseType.FORWARD, forward);
        }

        return new CommandResult(CommandResult.ResponseType.FORWARD, forward);
    }
}
