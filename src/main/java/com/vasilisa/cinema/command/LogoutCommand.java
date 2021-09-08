package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("loggedUser");
            session.invalidate();
        }

        return new CommandResult(CommandResult.ResponseType.REDIRECT, Path.PAGE__LOGIN);
    }
}
