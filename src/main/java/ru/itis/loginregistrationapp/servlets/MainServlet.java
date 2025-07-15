package ru.itis.loginregistrationapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.loginregistrationapp.service.UserService;

import java.io.IOException;

public class MainServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Логика для главной страницы
        getServletContext().getRequestDispatcher("/WEB-INF/main.jsp").forward(req, resp);
    }
}
