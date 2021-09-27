package com.vasilisa.cinema;

import com.vasilisa.cinema.command.Command;
import com.vasilisa.cinema.command.CommandContainer;
import com.vasilisa.cinema.command.CommandResult;
import com.vasilisa.cinema.command.UpdateUserProfileCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import static com.vasilisa.cinema.command.CommandResult.ResponseType.REDIRECT;
import static com.vasilisa.cinema.command.CommandResult.ResponseType.FORWARD;

/**
 * Main servlet controller.
 */

public class Controller extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(UpdateUserProfileCommand.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Controller starts");

        // extract command name from the request
        String commandName = request.getParameter("command");
        logger.trace("Request parameter: command --> " + commandName);

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);
        logger.trace("Obtained command --> " + command);

        // execute command and get forward address
        CommandResult commandResult = command.execute(request, response);

        // if the address is not null go to the address
        if (commandResult != null) {
            String page = commandResult.getPage();
            logger.debug("Go to the page --> " + page);
            if (commandResult.getResponseType().equals(REDIRECT)) {
                logger.debug("Redirect command");
                response.sendRedirect(request.getContextPath() + page);
            } else if (commandResult.getResponseType().equals(FORWARD)) {
                logger.debug("Forward command");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
                requestDispatcher.forward(request, response);
            }
        }

        logger.debug("Controller finished");
    }
}