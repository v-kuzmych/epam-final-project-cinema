package com.vasilisa.cinema.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Switch language command
 */

public class SwitchLanguageCommand implements Command {

    private static final Logger logger = LogManager.getLogger(SwitchLanguageCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        String newLocale = request.getParameter("param");
        HttpSession session = request.getSession(false);
        String currentLocale = (String) session.getAttribute("locale");

        if (!newLocale.equals(currentLocale)) {
            if ("ua".equals(newLocale)) {
                session.setAttribute("locale", "uk_UA");
            } else {
                session.setAttribute("locale", "en_US");
            }
        }

        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.REDIRECT, request.getHeader("Referer"));
    }
}
