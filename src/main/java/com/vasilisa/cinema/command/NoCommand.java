package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
//        log.debug("Command starts");
//
        String errorMessage = "No such command";
        request.setAttribute("errorMessage", errorMessage);
//        log.error("Set the request attribute: errorMessage --> " + errorMessage);
//        log.debug("Command finished");

        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ERROR_PAGE);
    }
}
