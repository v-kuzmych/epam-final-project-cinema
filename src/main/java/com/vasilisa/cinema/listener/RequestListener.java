package com.vasilisa.cinema.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.ServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Request encoding listener
 */

public class RequestListener implements ServletRequestListener {

    private static final Logger logger = LogManager.getLogger(RequestListener.class);

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        ServletRequest request = servletRequestEvent.getServletRequest();
        try {
            if (request.getCharacterEncoding() == null) {
                logger.error("Set character encoding UTF-8");
                request.setCharacterEncoding("UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            logger.warn(e.getMessage());
            logger.info("Using default request encoding (not utf-8)");
        }
    }

}
