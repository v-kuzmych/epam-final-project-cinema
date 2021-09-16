package com.vasilisa.cinema.command;

import database.FilmDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteFilmCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String errorMessage = null;
        String page = "/controller?command=admin_films_page";
        int filmId = Integer.parseInt(request.getParameter("id"));

        if (!new FilmDao().delete(filmId)) {
            errorMessage = "Не вдалося видалити фільм";
            request.setAttribute("errorMessage", errorMessage);
        }

        return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
    }
}
