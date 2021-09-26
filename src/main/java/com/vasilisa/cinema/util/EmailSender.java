package com.vasilisa.cinema.util;

import org.apache.commons.mail.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmailSender {

    private static final Logger logger = LogManager.getLogger(EmailSender.class);

    public static synchronized void sendPasswordRecoveryEmail(String receiver, String url) throws EmailException {
        logger.debug("Send email starts");

        HtmlEmail email = new HtmlEmail();
        // Configuration
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("verifier.multiflex@gmail.com","eafu4814"));

        // Required for gmail
        email.setSSLOnConnect(true);

        // Sender
        email.setFrom("verifier.multiflex@gmail.com");

        // Email title
        email.setSubject("Multiflex password recovery");

        // Email message.
        email.setHtmlMsg("If you have forgotten your password and want to reset it, <a href=\"" + url + "\" >follow the link</a>.\n" +
                        "Otherwise, ignore the letter.\n" +
                        "Please note that the link will only be valid for 10 minutes.");

        // Receiver
        email.addTo(receiver);
        email.send();

        logger.debug("Send email finished");
    }
}