package com.vasilisa.cinema.command;

import bean.Film;
import bean.Seance;
import com.vasilisa.cinema.Path;
import database.SeanceDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetScheduleForUserCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        String locale = (String) session.getAttribute("locale");

        List<Seance> seances = new SeanceDao().getAll(locale);

        Map<Film, List<Seance>> scheduleMap = seances.stream().collect(Collectors.groupingBy(
                Seance::getFilm,
                LinkedHashMap::new,
                Collectors.mapping(Function.identity(), Collectors.toList())
        ));

        Map<Film, Map<String, List<Seance>>> dashboard = new LinkedHashMap<>();

        for (Map.Entry<Film, List<Seance>> entry : scheduleMap.entrySet()) {
            Film film = entry.getKey();
            Map<String, List<Seance>> groupedSeances = entry.getValue().stream().collect(Collectors.groupingBy(
                    Seance::getFormatedDate,
                    LinkedHashMap::new,
                    Collectors.mapping(Function.identity(), Collectors.toList())
            ));
            dashboard.put(film, groupedSeances);
        }
        request.setAttribute("schedule", dashboard);

        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__SCHEDULE);
    }
}
