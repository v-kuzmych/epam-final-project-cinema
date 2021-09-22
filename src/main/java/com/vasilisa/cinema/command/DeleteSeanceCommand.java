package com.vasilisa.cinema.command;

import com.vasilisa.cinema.dao.SeanceDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteSeanceCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String errorMessage = null;
        int seanceId = Integer.parseInt(request.getParameter("id"));
        int filmId = Integer.parseInt(request.getParameter("film_id"));
        String page = "/controller?command=film_edit&tab=schedule&id="+filmId;

        if (!new SeanceDao().delete(seanceId)) {
            errorMessage = "Не вдалося видалити сеанс";
            request.setAttribute("errorMessage", errorMessage);
        }

        return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
    }
}
