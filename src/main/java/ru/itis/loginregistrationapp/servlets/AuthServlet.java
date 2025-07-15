package ru.itis.loginregistrationapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.loginregistrationapp.models.User;
import ru.itis.loginregistrationapp.service.UserService;
import ru.itis.loginregistrationapp.utility.CookieNames;
import ru.itis.loginregistrationapp.utility.CookieUtility;

import java.io.IOException;

import java.util.Optional;


public class AuthServlet extends HttpServlet {

    private UserService userService;


    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Проверка наличия куки и авторизации
        Optional<Long> userIdFromCookies = CookieUtility.getUserIdFromCookies(req, resp, userService);

        if (userIdFromCookies.isPresent()) {
            // Если пользователь уже авторизован, перенаправляем на профиль
            resp.sendRedirect(req.getContextPath() + "/profile/" + userIdFromCookies.get());
            return;
        }

        // Если пользователь не авторизован, показываем страницу входа
        req.getRequestDispatcher("WEB-INF/auth.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        Optional<User> loginUser = userService.loginUser(email, password);
        if (loginUser.isPresent()) {
            Cookie userCookie = new Cookie(CookieNames.AUTHENTICATION.getNameCookie(), loginUser.get().getId().toString());
            CookieUtility.configureSecureToken(userCookie, req.getParameter("rememberMe").equals("on"));
            resp.addCookie(userCookie);
            resp.sendRedirect(req.getContextPath() + "/profile/" + loginUser.get().getId());
        } else {
            req.setAttribute("errorMessage", "Неверный email или пароль");
            req.getRequestDispatcher("/WEB-INF/auth.jsp").forward(req, resp);
        }
    }


}
