package com.vasilisa.cinema.command;

import entity.Film;
import entity.Seance;
import com.vasilisa.cinema.Path;
import database.SeanceDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetScheduleCommand implements Command {
    public static final String[] DATE_FILTER_VALUES = new String[] {"today","tomorrow","week","month"};

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        String locale = (String) session.getAttribute("locale");
        String dateFilter = request.getParameter("dateFilter");
        if (dateFilter == null || !Arrays.stream(DATE_FILTER_VALUES).anyMatch(dateFilter::equals)) {
            dateFilter = DATE_FILTER_VALUES[0];
        }
        List<Seance> seances = new SeanceDao().getAll(locale, dateFilter);

        Map<Film, List<Seance>> scheduleMap = seances.stream().collect(Collectors.groupingBy(
                Seance::getFilm,
                LinkedHashMap::new,
                Collectors.mapping(Function.identity(), Collectors.toList())
        ));

        Map<Film, Map<String, List<Seance>>> dashboard = new LinkedHashMap<>();

        for (Map.Entry<Film, List<Seance>> entry : scheduleMap.entrySet()) {
            Film film = entry.getKey();
            Map<String, List<Seance>> groupedSeances = entry.getValue().stream().collect(Collectors.groupingBy(
                    Seance::getFormattedDate,
                    LinkedHashMap::new,
                    Collectors.mapping(Function.identity(), Collectors.toList())
            ));
            dashboard.put(film, groupedSeances);
        }
        request.setAttribute("schedule", dashboard);
        request.setAttribute("sitePage", "schedule");
        request.setAttribute("dateFilter", dateFilter);

        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__SCHEDULE);
    }
}
