package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.FilmDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  Delete film by admin and redirect on films list page
 */

public class DeleteFilmCommand implements Command {

    private static final Logger logger = LogManager.getLogger(DeleteFilmCommand.class);

    private FilmDao filmDao = new FilmDao();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        int filmId = Integer.parseInt(request.getParameter("id"));

        if (!filmDao.delete(filmId)) {
            String errorMessage = "Failed to delete movie";
            logger.error(errorMessage);
            request.setAttribute("errorMessage", errorMessage);
            return new CommandResult(CommandResult.ResponseType.REDIRECT, Path.PAGE__ERROR_PAGE);
        }

        logger.debug("The film was successfully deleted");
        return new CommandResult(CommandResult.ResponseType.REDIRECT, Path.COMMAND_SHOW_FILMS_LIST);
    }
}
