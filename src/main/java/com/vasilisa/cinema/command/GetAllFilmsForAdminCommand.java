package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  Show films list page for admin
 */

public class GetAllFilmsForAdminCommand implements Command {

    private static final Logger logger = LogManager.getLogger(GetAllFilmsForAdminCommand.class);

    private static final int LIMIT = 8;

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        new GetAllFilmsForUserCommand().getAllFilms(request, LIMIT);

        request.setAttribute("adminPage", "films");
        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ADMIN_FILMS_LIST);
    }
}
