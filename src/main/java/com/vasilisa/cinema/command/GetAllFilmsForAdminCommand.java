package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import database.FilmDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAllFilmsForAdminCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("films", new FilmDao().getAll());
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ADMIN_FILMS_LIST);
    }
}
