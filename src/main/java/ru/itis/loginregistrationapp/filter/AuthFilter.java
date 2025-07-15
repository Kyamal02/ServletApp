package ru.itis.loginregistrationapp.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.loginregistrationapp.service.UserService;
import ru.itis.loginregistrationapp.utility.CookieUtility;

import java.io.IOException;
import java.util.Optional;

public class AuthFilter implements Filter {

    private UserService userService;
    private ServletContext servletContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userService = (UserService) filterConfig.getServletContext().getAttribute("userService");
        servletContext = filterConfig.getServletContext();
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        if (shouldNotFilter(req)) {
            filterChain.doFilter(req, res);
            return;
        }
        Optional<Long> userIdFromCookies = CookieUtility.getUserIdFromCookies(req, res, userService);

        if (userIdFromCookies.isPresent()) {
            res.sendRedirect(req.getContextPath() + "/profile/" + userIdFromCookies.get());
            return;
        }

        filterChain.doFilter(req, res);
    }

    private boolean shouldNotFilter(HttpServletRequest req) {
        String path = req.getRequestURI().substring(req.getContextPath().length());
        return path.endsWith(".js") || path.endsWith(".css") || path.startsWith("/profile") || path.startsWith("/logout");
    }


}



