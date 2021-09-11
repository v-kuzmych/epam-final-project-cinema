package com.vasilisa.cinema.command;

import bean.Film;
import bean.FilmDescription;
import database.FilmDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddFilmCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Film film = new Film();
        film.setImg(request.getParameter("file"));

        String langIds[] = request.getParameter("lang_ids").split(",");

        List<FilmDescription> fd = new ArrayList<>();

        for (String landId : langIds){
            fd.add(new FilmDescription(
                    Integer.parseInt(landId),
                    request.getParameter("name." + landId),
                    request.getParameter("desc." + landId)
            ));
        }

        film.setFilmDescriptions(fd);

        film = new FilmDao().create(film);
        String page = "/controller?command=film_edit&id=" + film.getId();
        return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
    }
}
