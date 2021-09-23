package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.SeanceDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteSeanceCommand implements Command {

    private SeanceDao seanceDao = new SeanceDao();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        int seanceId = Integer.parseInt(request.getParameter("id"));
        int filmId = Integer.parseInt(request.getParameter("film_id"));

        if (!seanceDao.delete(seanceId)) {
            String errorMessage = "Не вдалося видалити сеанс";
            request.setAttribute("errorMessage", errorMessage);
            return new CommandResult(CommandResult.ResponseType.REDIRECT, Path.PAGE__ERROR_PAGE);
        }

        String page = "/controller?command=film_edit&tab=schedule&id="+filmId;
        return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
    }
}
