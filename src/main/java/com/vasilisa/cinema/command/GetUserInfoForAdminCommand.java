package com.vasilisa.cinema.command;

import bean.User;
import com.vasilisa.cinema.Path;
import database.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetUserInfoForAdminCommand implements Command{

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        User user = new UserDao().get(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("user", user);
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__USER_INFO);
    }
}
