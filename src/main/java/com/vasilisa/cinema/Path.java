package com.vasilisa.cinema;

public final class Path {

    // site pages
    public static final String PAGE__LOGIN = "/login.jsp";
    public static final String PAGE__REGISTER = "/register.jsp";
    public static final String PAGE__PROFILE = "/user/profile.jsp";
    public static final String PAGE__ERROR_PAGE = "/error_page.jsp";
    public static final String PAGE__FILMS_LIST = "/films.jsp";
    public static final String PAGE__SCHEDULE = "/schedule.jsp";
    public static final String PAGE__ORDER = "/order.jsp";

    // admin pages
    public static final String PAGE__ADMIN_DASHBOARD = "/admin/dashboard.jsp";
    public static final String PAGE__ADMIN_FILMS_LIST = "/admin/films/list.jsp";
    public static final String PAGE__ADD_FILM = "/admin/films/add.jsp";
    public static final String PAGE__EDIT_FILM = "/admin/films/edit.jsp";
    public static final String PAGE__ADMIN_SCHEDULE = "/admin/schedule.jsp";
    public static final String PAGE__USERS_LIST = "/admin/users/list.jsp";
    public static final String PAGE__USER_INFO = "/admin/users/info.jsp";

    // command
    public static final String COMMAND_SHOW_PROFILE = "/controller?command=profile";
}
