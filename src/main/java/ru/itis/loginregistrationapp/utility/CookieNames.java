package ru.itis.loginregistrationapp.utility;

public enum CookieNames {
    AUTHENTICATION("AUTH_COOKIE");


    final private String nameCookie;

    CookieNames(String nameCookie) {
        this.nameCookie = nameCookie;
    }

    public String getNameCookie() {
        return nameCookie;
    }
}
