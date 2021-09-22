package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.FilmDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteFilmCommand implements Command{

    private FilmDao filmDao = new FilmDao();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String errorMessage = null;
        String page = Path.PAGE__ADMIN_FILMS_LIST;
        int filmId = Integer.parseInt(request.getParameter("id"));

        if (!filmDao.delete(filmId)) {
            errorMessage = "Не вдалося видалити фільм";
            request.setAttribute("errorMessage", errorMessage);
        }

        return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
    }
}
