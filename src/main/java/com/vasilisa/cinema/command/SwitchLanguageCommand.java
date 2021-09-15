package com.vasilisa.cinema.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SwitchLanguageCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
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

        return new CommandResult(CommandResult.ResponseType.REDIRECT, request.getHeader("Referer"));
    }
}
