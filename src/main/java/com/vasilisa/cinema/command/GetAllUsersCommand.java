package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.UserDao;
import com.vasilisa.cinema.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  Get all users for admin
 */

public class GetAllUsersCommand implements Command {

    private static final Logger logger = LogManager.getLogger(GetAllUsersCommand.class);

    private UserDao userDao = new UserDao();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        List<User> users = userDao.getAll();

        request.setAttribute("users", users);
        request.setAttribute("adminPage", "users");

        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__USERS_LIST);
    }
}
