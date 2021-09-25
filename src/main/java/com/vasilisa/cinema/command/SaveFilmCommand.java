package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.entity.Film;
import com.vasilisa.cinema.entity.FilmDescription;
import com.vasilisa.cinema.dao.FilmDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class SaveFilmCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        // error handler
        String errorMessage = null;
        String errorPage = Path.PAGE__ERROR_PAGE;

        String languageIds[] = request.getParameterValues("languageIds");

        Film film = new Film();
        if(request.getParameter("id") != null) {
            film.setId(Integer.parseInt(request.getParameter("id")));
        }

        film.setImg(request.getParameter("img_url"));
        film.setDuration(Integer.parseInt(request.getParameter("duration")));

        List<FilmDescription> fd = new ArrayList<>();
        for (String langId : languageIds){
            fd.add(new FilmDescription(
                    Integer.parseInt(langId),
                    request.getParameter("name" + langId),
                    request.getParameter("desc" + langId)
            ));
        }
        film.setFilmDescriptions(fd);

        if (!new FilmDao().save(film)) {
            errorMessage = "Failed to save movie";
            request.setAttribute("errorMessage", errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, errorPage);
        }

        String page = "/controller?command=film_edit&id=" + film.getId();
        return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
    }
}
