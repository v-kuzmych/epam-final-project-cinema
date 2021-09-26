package com.vasilisa.cinema.command;

import com.vasilisa.cinema.entity.Film;
import com.vasilisa.cinema.entity.Seance;
import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.SeanceDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *  Get schedule for user
 */

public class GetScheduleCommand implements Command {

    private static final Logger logger = LogManager.getLogger(GetScheduleCommand.class);

    public static final String[] DATE_FILTER_VALUES = new String[] {"today","tomorrow","week","month"};

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        HttpSession session = request.getSession(false);
        String locale = (String) session.getAttribute("locale");
        String dateFilter = request.getParameter("dateFilter");

        Map<Film, Map<String, List<Seance>>> mappedSeances = getMappedSeances(locale, dateFilter, null);

        request.setAttribute("schedule", mappedSeances);
        request.setAttribute("sitePage", "schedule");
        request.setAttribute("dateFilter", dateFilter);

        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__SCHEDULE);
    }

    /**
     *
     * @param locale
     * @param dateFilter
     * @param filteredFilm
     * @return grouped seances by films and date
     */
    public Map<Film, Map<String, List<Seance>>> getMappedSeances(String locale, String dateFilter, Film filteredFilm) {
        if (dateFilter == null || !Arrays.stream(DATE_FILTER_VALUES).anyMatch(dateFilter::equals)) {
            dateFilter = DATE_FILTER_VALUES[0];
        }

        // get list of all seances
        List<Seance> seances = new SeanceDao().getAll(locale, dateFilter, filteredFilm);

        // group seances by film
        Map<Film, List<Seance>> scheduleMap = seances.stream().collect(Collectors.groupingBy(
                Seance::getFilm,
                LinkedHashMap::new,
                Collectors.mapping(Function.identity(), Collectors.toList())
        ));

        // films seances group by date
        Map<Film, Map<String, List<Seance>>> mappedSeances = new LinkedHashMap<>();
        for (Map.Entry<Film, List<Seance>> entry : scheduleMap.entrySet()) {
            Film film = entry.getKey();
            Map<String, List<Seance>> groupedSeances = entry.getValue().stream().collect(Collectors.groupingBy(
                    Seance::getFormattedDate,
                    LinkedHashMap::new,
                    Collectors.mapping(Function.identity(), Collectors.toList())
            ));
            mappedSeances.put(film, groupedSeances);
        }

        return mappedSeances;
    }
}
