package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.entity.Film;
import com.vasilisa.cinema.entity.FilmDescription;
import com.vasilisa.cinema.dao.FilmDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *  Save film by admin and redirect on this film edit page
 *  Use for add and update film
 */

public class SaveFilmCommand implements Command {

    private static final Logger logger = LogManager.getLogger(SaveFilmCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        // error handler
        String errorMessage = null;
        String errorPage = Path.PAGE__ERROR_PAGE;

        String languageIds[] = request.getParameterValues("languageIds");

        Film film = new Film();
        // if id exist then it is updating, else adding
        if(request.getParameter("id") != null) {
            logger.debug("Updating film...");
            film.setId(Integer.parseInt(request.getParameter("id")));
        }

        film.setImg(request.getParameter("img_url"));
        film.setDuration(Integer.parseInt(request.getParameter("duration")));

        // set film name and description by different language
        List<FilmDescription> fd = new ArrayList<>();
        for (String langId : languageIds){
            fd.add(new FilmDescription(
                    Integer.parseInt(langId),
                    request.getParameter("name" + langId),
                    request.getParameter("desc" + langId)
            ));
        }
        film.setFilmDescriptions(fd);

        if (!new FilmDao().save(film)) {
            errorMessage = "Failed to save movie";
            logger.error(errorMessage);
            request.setAttribute("errorMessage", errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, errorPage);
        }

        String page = "/controller?command=film_edit&id=" + film.getId();
        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
    }
}
