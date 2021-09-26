package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.HallDao;
import com.vasilisa.cinema.entity.Hall;
import com.vasilisa.cinema.entity.Seance;
import com.vasilisa.cinema.dao.SeanceDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *  Add seance by admin and redirect on film edit page
 */

public class AddSeanceCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AddSeanceCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        int filmId = Integer.parseInt(request.getParameter("id"));
        int price = Integer.parseInt(request.getParameter("price"));
        String date = request.getParameter("date") + " " + request.getParameter("time");

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, fmt);
        String page = "/controller?command=film_edit&tab=schedule&id=" + filmId;

        // check created date
        if (dateTime.isBefore(LocalDateTime.now())) {
            logger.error("DateTime is before now");
            String errorMessage = "Sorry, the time must be greater than the present";
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("prevPage", page);
            return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ERROR_PAGE);
        }

        // check free time frame
        if (!new SeanceDao().checkSeances(dateTime, filmId)) {
            logger.error("There is already a seance in this time frame");
            String errorMessage = "Sorry, there is already a seance in this time frame, check schedule and select other time";
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("prevPage", page);
            return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ERROR_PAGE);
        }

        Seance seance = new Seance();
        seance.setFilmId(filmId);
        seance.setDate(dateTime);
        seance.setPrice(price);

        // now we have only one hall
        // calculate its capacity
        int hallId = 1;
        Hall hall = new HallDao().get(hallId);
        seance.setHall(hall);
        seance.setFreeSeats(hall.getNumberOfSeats() * hall.getNumberOfRows());

        // create seance
        if (!new SeanceDao().create(seance)) {
            logger.error("Failed to create seance");
            String errorMessage = "Sorry, seance not created";
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("prevPage", page);
            return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ERROR_PAGE);
        }

        logger.debug("The seance was successfully created");
        return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
    }
}
