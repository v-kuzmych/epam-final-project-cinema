package com.vasilisa.cinema.command;

import entity.Seance;
import database.SeanceDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddSeanceCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("date") + " " + request.getParameter("time"), fmt);

        int filmId = Integer.parseInt(request.getParameter("id"));
        int price = Integer.parseInt(request.getParameter("price"));
        Seance seance = new Seance();
        seance.setFilmId(filmId);
        seance.setDate(dateTime);
        seance.setPrice(price);
        new SeanceDao().create(seance);

        String page = "/controller?command=film_edit&tab=schedule&id=" + filmId;
        return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
    }
}
