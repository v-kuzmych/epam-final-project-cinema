package com.vasilisa.cinema.command;

import com.vasilisa.cinema.entity.Language;
import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.LanguageDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAddFilmPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        List<Language> languages = new LanguageDao().getAll();
        request.setAttribute("languages", languages);
        request.setAttribute("adminPage", "films");

        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ADD_FILM);
    }
}
