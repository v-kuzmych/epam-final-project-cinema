package com.vasilisa.cinema;

public final class Path {

    // site pages
    public static final String PAGE__LOGIN = "/login.jsp";
    public static final String PAGE__REGISTER = "/register.jsp";
    public static final String PAGE__PROFILE = "/WEB-INF/site/user/profile.jsp";
    public static final String PAGE__ERROR_PAGE = "/error_page.jsp";
    public static final String PAGE__FILMS_LIST = "/WEB-INF/site/films.jsp";
    public static final String PAGE__SCHEDULE = "/WEB-INF/site/schedule.jsp";
    public static final String PAGE__ORDER = "/WEB-INF/site/order.jsp";
    public static final String PAGE__SUCCESS_ORDER = "/success_order.jsp";

    // admin pages
    public static final String PAGE__ADMIN_DASHBOARD = "/WEB-INF/admin/dashboard.jsp";
    public static final String PAGE__ADMIN_FILMS_LIST = "/WEB-INF/admin/films/list.jsp";
    public static final String PAGE__ADD_FILM = "/WEB-INF/admin/films/add.jsp";
    public static final String PAGE__EDIT_FILM = "/WEB-INF/admin/films/edit.jsp";
    public static final String PAGE__USERS_LIST = "/WEB-INF/admin/users/list.jsp";
    public static final String PAGE__USER_INFO = "/WEB-INF/admin/users/info.jsp";

    // command
    public static final String COMMAND_SHOW_PROFILE = "/controller?command=profile";
    public static final String COMMAND_SHOW_DASHBOARD = "/controller?command=admin_dashboard";
}
