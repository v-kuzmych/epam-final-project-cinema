package com.vasilisa.cinema;

import bean.*;
import com.vasilisa.cinema.command.Command;
import com.vasilisa.cinema.command.CommandContainer;
import com.vasilisa.cinema.command.CommandResult;
import database.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import static com.vasilisa.cinema.command.CommandResult.ResponseType.REDIRECT;
import static com.vasilisa.cinema.command.CommandResult.ResponseType.FORWARD;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        log.debug("Controller starts");

        // extract command name from the request
        String commandName = request.getParameter("command");
//        log.trace("Request parameter: command --> " + commandName);

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);
//        log.trace("Obtained command --> " + command);

        // execute command and get forward address
        CommandResult commandResult = command.execute(request, response);

//        log.trace("Forward address --> " + forward);

//        log.debug("Controller finished, now go to forward address --> " + forward);

        // if the address is not null go to the address
        if (commandResult != null) {
            String page = commandResult.getPage();
            if (commandResult.getResponseType().equals(REDIRECT)) {
                response.sendRedirect(request.getContextPath() + page);
            } else if (commandResult.getResponseType().equals(FORWARD)) {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
                requestDispatcher.forward(request, response);
            }
        }
    }
}