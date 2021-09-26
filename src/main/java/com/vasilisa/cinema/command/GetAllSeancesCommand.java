package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.SeanceDao;
import com.vasilisa.cinema.entity.Seance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *  Get all seances for user
 */

public class GetAllSeancesCommand implements Command {

    private static final Logger logger = LogManager.getLogger(GetAllSeancesCommand.class);

    private SeanceDao seanceDao = new SeanceDao();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        HttpSession session = request.getSession(false);
        String locale = (String) session.getAttribute("locale");
        String dateFilter = request.getParameter("dateFilter");

        List<Seance> seances = seanceDao.getAll(locale, dateFilter, null);

        request.setAttribute("seances", seances);
        request.setAttribute("sitePage", "seances");
        request.setAttribute("dateFilter", dateFilter);

        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__SEANCES);
    }
}
