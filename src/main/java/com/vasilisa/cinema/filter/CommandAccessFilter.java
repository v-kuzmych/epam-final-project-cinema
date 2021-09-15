package com.vasilisa.cinema.filter;

import com.vasilisa.cinema.Path;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Security filter
 */

public class CommandAccessFilter  implements Filter {
    //private static final Logger log = Logger.getLogger(CommandAccessFilter.class);

    // commands access
    private static Map<String, List<String>> accessMap = new HashMap<>();
    private static List<String> commons = new ArrayList<>();
    private static List<String> outOfControl = new ArrayList<>();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        log.debug("Filter initialization starts");

        // roles
        accessMap.put("admin", asList(filterConfig.getInitParameter("admin")));
        accessMap.put("user", asList(filterConfig.getInitParameter("user")));
//        log.trace("Access map --> " + accessMap);

        // commons
        commons = asList(filterConfig.getInitParameter("common"));
//        log.trace("Common commands --> " + commons);

        // out of control
        outOfControl = asList(filterConfig.getInitParameter("out-of-control"));
//        log.trace("Out of control commands --> " + outOfControl);

//        log.debug("Filter initialization finished");

    }

    /**
     * Extracts parameter values from string.
     *
     * @param str
     *            parameter values string.
     * @return list of parameter values.
     */
    private List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) list.add(st.nextToken());
        return list;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        log.debug("Filter starts");

        if (accessAllowed(request)) {
//            log.debug("Filter finished");
            chain.doFilter(request, response);
        } else {
            String errorMessasge = "You do not have permission to access the requested resource";

            request.setAttribute("errorMessage", errorMessasge);
//            log.trace("Set the request attribute: errorMessage --> " + errorMessasge);

            request.getRequestDispatcher(Path.PAGE__ERROR_PAGE)
                    .forward(request, response);
        }

    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("command");
        if (commandName == null || commandName.isEmpty())
            return false;

        if (outOfControl.contains(commandName))
            return true;

        HttpSession session = httpRequest.getSession(false);
        if (session == null)
            return false;

        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null)
            return false;

        return accessMap.get(userRole).contains(commandName)
                || commons.contains(commandName);
    }


    @Override
    public void destroy() {
//        log.debug("Filter destruction starts");
        // do nothing
//        log.debug("Filter destruction finished");
    }
}
