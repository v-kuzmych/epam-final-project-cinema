package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Logout command.
 */

public class LogoutCommand implements Command {

    private static final Logger logger = LogManager.getLogger(LogoutCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("loggedUser");
            session.invalidate();
        }

        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.REDIRECT, Path.PAGE__LOGIN);
    }
}
