package ru.itis.loginregistrationapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.loginregistrationapp.models.User;
import ru.itis.loginregistrationapp.service.UserService;
import ru.itis.loginregistrationapp.utility.CookieUtility;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ProfileServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<Long> userIdFromCookies = CookieUtility.getUserIdFromCookies(req, resp, userService);

        if (userIdFromCookies.isPresent()) {
            String pathInfo = req.getPathInfo(); // Например, "/1"
            if (pathInfo == null || pathInfo.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL");
                return;
            }
            //TODO исправить на перенаправление
            long userIdFromUrl;
            try {
                userIdFromUrl = Long.parseLong(pathInfo.substring(1)); // Убираем первый символ "/"
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID in URL");
                return;
            }


            if (userIdFromUrl != userIdFromCookies.get()) {
                resp.sendRedirect(req.getContextPath() + "/profile/" + userIdFromCookies.get());
                return;
            }

            Optional<User> userById = userService.getUserById(userIdFromCookies.get());
            if (userById.isPresent()) {
                List<User> users = userService.getAllUsersExceptCurrent(userById.get().getEmail());
                req.setAttribute("user", userById.get());
                req.setAttribute("users", users);
                req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/auth");
        }
    }

}

