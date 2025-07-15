package ru.itis.loginregistrationapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.loginregistrationapp.exception.PasswordMismatchException;
import ru.itis.loginregistrationapp.service.UserService;

import java.io.IOException;

public class RegistrationServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init()  {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String rePassword = req.getParameter("rePassword");

        try {
            userService.registerUser(email, password, rePassword);
            req.getRequestDispatcher("/WEB-INF/auth.jsp").forward(req, resp);
        } catch (PasswordMismatchException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/registration.jsp").forward(req, resp);
        }

    }

}
