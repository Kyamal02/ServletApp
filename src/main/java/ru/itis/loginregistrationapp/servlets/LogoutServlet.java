package ru.itis.loginregistrationapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.loginregistrationapp.service.UserService;
import ru.itis.loginregistrationapp.utility.CookieNames;
import ru.itis.loginregistrationapp.utility.CookieUtility;

import java.io.IOException;
import java.util.Optional;

public class LogoutServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Optional<Long> userIdFromCookies = CookieUtility.getUserIdFromCookies(req, resp, userService);
        if (userIdFromCookies.isPresent()) {
            Cookie cookie = new Cookie(CookieNames.AUTHENTICATION.getNameCookie(), "");
            CookieUtility.configureSecureToken(cookie, false);
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
        }
        resp.sendRedirect(req.getContextPath() + "/");
    }

}
