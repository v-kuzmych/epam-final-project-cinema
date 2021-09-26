package com.vasilisa.cinema.command;

import com.vasilisa.cinema.entity.Language;
import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.LanguageDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Show page for adding film by admin
 */

public class ShowAddFilmPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowAddFilmPageCommand.class);

    private LanguageDao languageDao = new LanguageDao();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        List<Language> languages = languageDao.getAll();
        request.setAttribute("languages", languages);
        request.setAttribute("adminPage", "films");

        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ADD_FILM);
    }
}
