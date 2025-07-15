package ru.itis.loginregistrationapp.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private Long id;
    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email) {
        this.email = email;
    }

    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

    public static User getObjectFromResultSet(ResultSet rs) throws SQLException {
        return new User(rs.getLong("id"), rs.getString("email"), rs.getString("password_hash"));
    }
}
