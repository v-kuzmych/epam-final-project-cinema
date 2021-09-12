package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowDashboardPageCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ADMIN_DASHBOARD);
    }
}
