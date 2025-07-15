package ru.itis.loginregistrationapp.utility;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.loginregistrationapp.service.UserService;

import java.util.Optional;

public class CookieUtility {


    public static void configureSecureToken(Cookie cookie, boolean rememberMe) {
        if (rememberMe) {
            cookie.setMaxAge(60 * 60 * 24 * 7);
        }
        cookie.setHttpOnly(true);
        cookie.setPath("/");
    }

    public static Optional<Long> getUserIdFromCookies(HttpServletRequest req, HttpServletResponse resp, UserService userService) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return Optional.empty();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(CookieNames.AUTHENTICATION.getNameCookie())) {
                try {
                    Long userId = Long.parseLong(cookie.getValue());
                    // Проверка существования пользователя в БД
                    if (userService.getUserById(userId).isPresent()) {
                        return Optional.of(userId);
                    } else {
                        // Удаление невалидной куки
                        Cookie invalidCookie = new Cookie(CookieNames.AUTHENTICATION.getNameCookie(), "");
                        invalidCookie.setMaxAge(0);
                        resp.addCookie(invalidCookie);
                    }
                } catch (NumberFormatException e) {
                    //TODO
                }
            }
        }
        return Optional.empty();
    }


}
