package com.vasilisa.cinema;

import bean.*;
import database.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private UserDao userDao;
    private FilmDao filmDao;
    private SeanceDao seanceDao;
    private LanguageDao languageDao;
    private FilmDescriptionDao filmDescriptionDao;

    public void init() {
        userDao = new UserDao();
        filmDao = new FilmDao();
        seanceDao = new SeanceDao();
        filmDescriptionDao = new FilmDescriptionDao();
        languageDao = new LanguageDao();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("switch_language".equals(command)) {
            switchLanguage(request);
            response.sendRedirect(request.getHeader("Referer"));
        } else if ("register".equals(command)) {
            register(request, response);
        } else if ("logout".equals(command)) {
            logout(request, response);
        } else if ("user_films_page".equals(command)) {
            getAllFilmsForUser(request, response);
        } else if ("admin_films_page".equals(command)) {
            getAllFilmsForAdmin(request, response);
        } else if ("admin_users_page".equals(command)) {
            getAllUsers(request, response);
        } else if ("user_info".equals(command)) {
            getUserById(request, response);
        } else if ("film_edit".equals(command)) {
            getFilmByIdForAdmin(request, response);
        } else if ("film_delete".equals(command)) {
            deleteFilm(request, response);
        } else if ("add_film".equals(command)) {
            addFilmPage(request, response);
        } else if ("save_film".equals(command)) {
            saveFilm(request, response);
        } else if ("add_seance".equals(command)) {
            addSeance(request, response);
        } else if ("schedule_page".equals(command)) {
            getSchedule(request, response);
        }
    }

    private void switchLanguage(HttpServletRequest request) {
        String newLocale = request.getParameter("param");
        HttpSession session = request.getSession(false);
        String currentLocale = (String) session.getAttribute("locale");

        if (!newLocale.equals(currentLocale)) {
            if ("en".equals(newLocale)) {
                session.setAttribute("locale", "en_US");
            } else {
                session.setAttribute("locale", "uk_UA");
            }
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        try {
            User newUser = userDao.create(user);
            HttpSession session = request.getSession();
            if (newUser != null) {
                session.setAttribute("loggedUser", newUser);
                response.sendRedirect("user/profile.jsp");
            } else {
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                out.println("<h1>error</h1>");
                out.println("</body></html>");
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("loggedUser");
            session.invalidate();
        }
        response.sendRedirect("/login");
    }

    public void getAllFilmsForUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("films", filmDao.getAll());
            request.getRequestDispatcher("/films.jsp").forward(request, response);
        } catch (IOException | ServletException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getAllFilmsForAdmin(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("films", filmDao.getAll());
            request.getRequestDispatcher("/admin/films/list.jsp").forward(request, response);
        } catch (IOException | ServletException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getAllUsers(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("users", userDao.getAll());
            request.getRequestDispatcher("/admin/users/list.jsp").forward(request, response);
        } catch (IOException | ServletException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getUserById(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = userDao.get(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("user", user);
            request.getRequestDispatcher("/admin/users/info.jsp").forward(request, response);
        } catch (ClassNotFoundException | ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    public void addFilmPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Language> languages = languageDao.getAll();
            request.setAttribute("languages", languages);
            request.getRequestDispatcher("/admin/films/add.jsp").forward(request, response);
        } catch (IOException | ClassNotFoundException | ServletException e) {
            e.printStackTrace();
        }
    }

    public void getFilmByIdForAdmin(HttpServletRequest request, HttpServletResponse response) {
        try {
            int film_id = Integer.parseInt(request.getParameter("id"));
            Film film = filmDao.get(film_id);
            List<FilmDescription> descriptions = filmDescriptionDao.getByFilmId(film_id);

            HttpSession session = request.getSession(false);
            String currentLocale = (String) session.getAttribute("locale");
            String[] localeAttr = currentLocale.split("_");
            List<Seance> seances = seanceDao.getByFilmId(film_id, new Locale(localeAttr[0], localeAttr[1]));
            Map<String, List<Seance>> seancesMap = seances.stream().collect(Collectors.groupingBy(
                            Seance::getFormatedDate,
                            LinkedHashMap::new,
                            Collectors.mapping(Function.identity(), Collectors.toList())
                    ));

            request.setAttribute("film", film);
            request.setAttribute("descriptions", descriptions);
            request.setAttribute("seances", seancesMap);
            request.getRequestDispatcher("/admin/films/edit.jsp").forward(request, response);
        } catch (ClassNotFoundException | ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFilm(HttpServletRequest request, HttpServletResponse response) {
        Film film = new Film();
//        film.deleteById(request.getParameter("id"));
    }

    public void saveFilm(HttpServletRequest request, HttpServletResponse response) {
        Film film = new Film();
        film.setImg("image_placeholder");

        String langIds[] = request.getParameter("lang_ids").split(",");

        List<FilmDescription> fd = new ArrayList<>();

        for (String landId : langIds){
            fd.add(new FilmDescription(
                    Integer.parseInt(landId),
                    request.getParameter("name." + landId),
                    request.getParameter("desc." + landId)
            ));
        }

        film.setFilmDescriptions(fd);

        try {
            film = filmDao.create(film);
            response.sendRedirect("/controller?command=film_edit&id=" + film.getId());
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public void addSeance(HttpServletRequest request, HttpServletResponse response) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("date") + " " + request.getParameter("time"), fmt);

        int filmId = Integer.parseInt(request.getParameter("id"));
        Seance seance = new Seance();
        seance.setFilmId(filmId);
        seance.setDate(dateTime);
        try {
            seanceDao.create(seance);
            response.sendRedirect("/controller?command=film_edit&tab=schedule&id=" + filmId);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public void getSchedule(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            String locale = (String) session.getAttribute("locale");

            List<Seance> seances = seanceDao.getAll(locale);

            Map<Film, List<Seance>> scheduleMap = seances.stream().collect(Collectors.groupingBy(
                    Seance::getFilm,
                    LinkedHashMap::new,
                    Collectors.mapping(Function.identity(), Collectors.toList())
            ));

            Map<Film, Map<String, List<Seance>>> dashboard = new LinkedHashMap<>();

            for (Map.Entry<Film, List<Seance>> entry : scheduleMap.entrySet()) {
                Film film = entry.getKey();
                Map<String, List<Seance>> groupedSeances = entry.getValue().stream().collect(Collectors.groupingBy(
                        Seance::getFormatedDate,
                        LinkedHashMap::new,
                        Collectors.mapping(Function.identity(), Collectors.toList())
                ));
                dashboard.put(film, groupedSeances);
            }

            request.setAttribute("schedule", dashboard);

            request.getRequestDispatcher("/admin/schedule.jsp").forward(request, response);
        } catch (IOException | ServletException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}