package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Show dashboard page for admin
 */

public class ShowDashboardPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowDashboardPageCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command done");
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ADMIN_DASHBOARD);
    }
}
