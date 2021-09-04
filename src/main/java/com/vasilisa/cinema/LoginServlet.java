package com.vasilisa.cinema;

import bean.User;
import database.UserDao;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "loginServlet", value = "/loginServlet")
public class LoginServlet extends HttpServlet {
    private UserDao userDao;

    public void init() {
        userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>login</h1>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        try {
            User loggedUser = userDao.login(user);
            if (loggedUser != null) {
                HttpSession session = request.getSession();
                session.setAttribute("loggedUser", loggedUser);

                if (session.getAttribute("locale") == null ) {
                    session.setAttribute("locale", "uk_UA");
                }

                if ("admin".equals(loggedUser.getRole())) {
                    response.sendRedirect("admin/dashboard.jsp");
                } else {
                    response.sendRedirect("user/profile.jsp");
                }

            } else {
                HttpSession session = request.getSession();
                //session.setAttribute("user", username);
                //response.sendRedirect("login.jsp");
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                out.println("<h1>error</h1>");
                out.println("</body></html>");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
