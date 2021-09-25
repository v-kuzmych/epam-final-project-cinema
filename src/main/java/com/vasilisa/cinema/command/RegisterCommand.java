package com.vasilisa.cinema.command;

import com.vasilisa.cinema.entity.User;
import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.UserDao;
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
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // error handler
        String errorMessage = null;
        String forward = Path.PAGE__ERROR_PAGE;
        request.setAttribute("prevPage", Path.PAGE__REGISTER);

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            errorMessage = "Failed to create new user";
            request.setAttribute("errorMessage", errorMessage);
            logger.error(errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, forward);
        }

        User user = new User();
        user.setName(name);
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

        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.FORWARD, forward);
    }
}
