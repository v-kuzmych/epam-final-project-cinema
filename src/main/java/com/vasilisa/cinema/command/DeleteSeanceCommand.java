package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.SeanceDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  Delete seance by admin and redirect on film edit page
 */

public class DeleteSeanceCommand implements Command {

    private static final Logger logger = LogManager.getLogger(DeleteSeanceCommand.class);

    private SeanceDao seanceDao = new SeanceDao();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        int seanceId = Integer.parseInt(request.getParameter("id"));
        int filmId = Integer.parseInt(request.getParameter("film_id"));

        if (!seanceDao.delete(seanceId)) {
            String errorMessage = "Failed to delete seance";
            logger.error(errorMessage);
            request.setAttribute("errorMessage", errorMessage);
            return new CommandResult(CommandResult.ResponseType.REDIRECT, Path.PAGE__ERROR_PAGE);
        }

        String page = "/controller?command=film_edit&tab=schedule&id="+filmId;
        logger.debug("The seance was successfully deleted");
        return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
    }
}
