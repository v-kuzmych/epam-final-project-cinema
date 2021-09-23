package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.UserDao;
import com.vasilisa.cinema.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetAllUsersCommand implements Command {

    private UserDao userDao = new UserDao();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = userDao.getAll();

        request.setAttribute("users", users);
        request.setAttribute("adminPage", "users");

        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__USERS_LIST);
    }
}
