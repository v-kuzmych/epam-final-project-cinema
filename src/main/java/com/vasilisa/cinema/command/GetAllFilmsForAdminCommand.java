package com.vasilisa.cinema.command;

import entity.Film;
import com.vasilisa.cinema.Path;
import database.FilmDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import static java.lang.Math.ceil;

public class GetAllFilmsForAdminCommand implements Command {
    private static final int LIMIT = 8;

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        String currentLocale = (String) session.getAttribute("locale");
        int filmsCount = new FilmDao().getFilmsCount();
        int pages = (int) ceil((float)filmsCount / LIMIT);

        String page = request.getParameter("page");
        int parsedPage;
        try {
            parsedPage = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            parsedPage = 1;
        }

        int currentPage = parsedPage > 0 ? parsedPage : 1;
        if (currentPage > pages) {
            currentPage = pages;
        }
        int offset = LIMIT * currentPage - LIMIT;

        List<Film> films = new FilmDao().getAll(currentLocale, LIMIT, offset);

        request.setAttribute("films", films);
        request.setAttribute("adminPage", "films");
        request.setAttribute("pages", pages);
        request.setAttribute("currentPage", currentPage);

        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ADMIN_FILMS_LIST);
    }
}
