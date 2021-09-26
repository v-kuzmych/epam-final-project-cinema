package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.PasswordRecoveryDao;
import com.vasilisa.cinema.dao.UserDao;
import com.vasilisa.cinema.entity.PasswordRecovery;
import com.vasilisa.cinema.entity.User;
import com.vasilisa.cinema.util.Md5HexEncryption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Change password command
 */

public class ChangePasswordCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ChangePasswordCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        String token = request.getParameter("token");
        String password = request.getParameter("password");

        // error handler
        String errorMessage = null;
        String forward = Path.PAGE__ERROR_PAGE;
        request.setAttribute("prevPage", "controller?controller?command=recovery_password&token=" + token);

        if (token == null || token.isEmpty() || password == null || (password.length() > 0 && password.length() < 3)) {
            errorMessage = "Invalid data";
            request.setAttribute("errorMessage", errorMessage);
            logger.error(errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, forward);
        }

        // check token
        PasswordRecovery pr = new PasswordRecoveryDao().get(token);
        if (pr == null) {
            errorMessage = "Token not found";
            request.setAttribute("errorMessage", errorMessage);
            logger.error(errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, forward);
        }

        // check user by id
        User user = new UserDao().get(pr.getUserId());
        if (user == null || pr.getUserId() != user.getId()) {
            errorMessage = "User not found";
            request.setAttribute("errorMessage", errorMessage);
            logger.error(errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, forward);
        }

        // update user password
        user.setPassword(new Md5HexEncryption().md5Hex(password));
        if (!new UserDao().update(user)) {
            errorMessage = "Cannot update user";
            request.setAttribute("errorMessage", errorMessage);
            logger.error(errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, forward);
        }

        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__SUCCESS_CHANGE_PASS);
    }
}
