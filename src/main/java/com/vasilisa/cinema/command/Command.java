package com.vasilisa.cinema.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Main interface for the Command pattern implementation.
 */

public interface Command {
    CommandResult execute(HttpServletRequest request, HttpServletResponse response);
}
