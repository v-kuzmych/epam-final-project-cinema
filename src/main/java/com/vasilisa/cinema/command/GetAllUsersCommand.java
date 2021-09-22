package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.database.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAllUsersCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("users", new UserDao().getAll());
        request.setAttribute("adminPage", "users");

        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__USERS_LIST);
    }
}
