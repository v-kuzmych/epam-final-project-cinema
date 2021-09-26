package com.vasilisa.cinema.command;

import com.vasilisa.cinema.entity.Film;
import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.FilmDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static java.lang.Math.ceil;

/**
 *  Show films list page for user
 */

public class GetAllFilmsForUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger(GetAllFilmsForUserCommand.class);

    private static final int LIMIT = 8;

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        getAllFilms(request, LIMIT);

        request.setAttribute("sitePage", "films");
        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__FILMS_LIST);
    }

    public void getAllFilms(HttpServletRequest request, int limit) {
        HttpSession session = request.getSession(false);
        String currentLocale = (String) session.getAttribute("locale");
        String page = request.getParameter("page");

        // get films count and check pages number
        int filmsCount = new FilmDao().getFilmsCount();
        int pages = (int) ceil((float)filmsCount / limit);
        int parsedPage;

        // check if page is number
        try {
            parsedPage = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            logger.error("Page isn't number");
            parsedPage = 1;
        }

        // check if page is in range between 1 and pages count
        int currentPage = parsedPage > 0 ? parsedPage : 1;
        if (currentPage > pages) {
            logger.error("Page isn't in range between 1 and pages count");
            currentPage = pages;
        }
        int offset = limit * currentPage - limit;

        // get films list
        List<Film> films = new FilmDao().getAll(currentLocale, limit, offset);

        logger.debug("Get films list");
        request.setAttribute("films", films);
        request.setAttribute("pages", pages);
        request.setAttribute("currentPage", currentPage);
    }

}
