package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.UserDao;
import com.vasilisa.cinema.entity.User;
import com.vasilisa.cinema.util.Md5HexEncryption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Update user data command
 */

public class UpdateUserProfileCommand implements Command {

    private static final Logger logger = LogManager.getLogger(UpdateUserProfileCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        HttpSession session = request.getSession();
        int loggedUserId = ((User) session.getAttribute("loggedUser")).getId();

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // error handler
        String errorMessage = null;
        String forward = Path.PAGE__ERROR_PAGE;
        request.setAttribute("prevPage", Path.COMMAND_SHOW_PROFILE);

        // check user id
        if (loggedUserId != id) {
            errorMessage = "Invalid action";
            logger.error(errorMessage);
            request.setAttribute("errorMessage", errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, forward);
        }

        // validate variables
        if (name == null || email == null || password == null ||
                name.isEmpty() || email.isEmpty() || (password.length() > 0 && password.length() < 3)) {
            errorMessage = "Failed to update user";
            logger.error(errorMessage);
            request.setAttribute("errorMessage", errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, forward);
        }

        User user = new UserDao().get(id);
        logger.debug("Found in DB: user --> " + user);
        user.setName(name);
        user.setEmail(email);
        if (!password.isEmpty()) {
            logger.debug("Change password");
            user.setPassword(new Md5HexEncryption().md5Hex(password));
        }

        if (!new UserDao().update(user)) {
            errorMessage = "Cannot update user with data: " + name + ", " + email + ", " + password;
            request.setAttribute("errorMessage", errorMessage);
            logger.error(errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, forward);
        }

        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.REDIRECT, Path.COMMAND_SHOW_PROFILE);
    }
}
