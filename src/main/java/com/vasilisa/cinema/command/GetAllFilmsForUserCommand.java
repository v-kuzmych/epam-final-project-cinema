package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import database.FilmDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAllFilmsForUserCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        String currentLocale = (String) session.getAttribute("locale");
        request.setAttribute("films", new FilmDao().getAllForUser(currentLocale));
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__FILMS_LIST);
    }
}
