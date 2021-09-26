package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.PasswordRecoveryDao;
import com.vasilisa.cinema.entity.PasswordRecovery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Check recovery link and set new password
 */

public class RecoveryPasswordCommand implements Command {

    private static final Logger logger = LogManager.getLogger(RecoveryPasswordCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        String token = request.getParameter("token");

        // error handler
        String errorMessage = null;
        String forward = Path.PAGE__ERROR_PAGE;
        request.setAttribute("prevPage", Path.PAGE__LOGIN);

        if (token == null || token.isEmpty()) {
            errorMessage = "Invalid token";
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

        request.setAttribute("token", pr.getToken());
        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__RECOVERY_PASSWORD);
    }
}
