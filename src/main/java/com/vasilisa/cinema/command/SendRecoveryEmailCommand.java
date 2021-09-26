package com.vasilisa.cinema.command;

import com.vasilisa.cinema.Path;
import com.vasilisa.cinema.dao.PasswordRecoveryDao;
import com.vasilisa.cinema.dao.UserDao;
import com.vasilisa.cinema.entity.PasswordRecovery;
import com.vasilisa.cinema.entity.User;
import com.vasilisa.cinema.util.EmailSender;
import org.apache.commons.mail.EmailException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Send recovery password email
 */

public class SendRecoveryEmailCommand implements Command {

    private static final Logger logger = LogManager.getLogger(SendRecoveryEmailCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        String email = request.getParameter("recoveryEmail");

        // error handler
        String errorMessage = null;
        String forward = Path.PAGE__ERROR_PAGE;
        request.setAttribute("prevPage", Path.PAGE__LOGIN);

        if (email == null || email.isEmpty()) {
            errorMessage = "Invalid email";
            request.setAttribute("errorMessage", errorMessage);
            logger.error(errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, forward);
        }

        // check user by email
        User user = new UserDao().checkByEmail(email);
        if (user == null) {
            errorMessage = "User not found. There is no such email";
            request.setAttribute("errorMessage", errorMessage);
            logger.error(errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, forward);
        }

        String token = UUID.randomUUID().toString();
        String recoveryHref = "http://localhost:8080/controller?command=recovery_password&token=" + token;

        // save token
        PasswordRecovery pr = new PasswordRecovery();
        pr.setUserId(user.getId());
        pr.setToken(token);
        if (!new PasswordRecoveryDao().create(pr)) {
            logger.error("Failed to create token");
            errorMessage = "Sorry, try again later";
            request.setAttribute("errorMessage", errorMessage);
            return new CommandResult(CommandResult.ResponseType.FORWARD, Path.PAGE__ERROR_PAGE);
        }

        // send email with recovery link
        try {
            EmailSender.sendPasswordRecoveryEmail(email, recoveryHref);
            logger.debug("Email send");
        } catch (EmailException e) {
            e.printStackTrace();
            logger.error("Get failed with error " + e.getMessage());
        }

        logger.debug("Command finished");
        return new CommandResult(CommandResult.ResponseType.REDIRECT, Path.PAGE__SUCCESS_SEND_RECOVERY_EMAIL);
    }
}
