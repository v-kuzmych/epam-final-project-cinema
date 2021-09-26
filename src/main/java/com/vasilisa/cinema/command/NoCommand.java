package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * No command.
 */

public class NoCommand implements Command {

    private static final Logger logger = LogManager.getLogger(NoCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
       logger.debug("Command starts");

        String errorMessage = "No such command";
        request.setAttribute("errorMessage", errorMessage);
        logger.error("Set the request attribute: errorMessage --> " + errorMessage);

        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ERROR_PAGE);
    }
}
