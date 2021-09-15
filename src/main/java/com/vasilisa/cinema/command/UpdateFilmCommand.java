package com.vasilisa.cinema.command;

import database.FilmDao;
import entity.Film;
import entity.FilmDescription;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class UpdateFilmCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Film film = new Film();
        film.setImg(request.getParameter("img_url"));
        film.setDuration(Integer.parseInt(request.getParameter("duration")));

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


        String page = "/controller?command=film_edit&id=" + film.getId();
        return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
    }
}
