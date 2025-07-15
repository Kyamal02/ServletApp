package ru.itis.loginregistrationapp.repository;

import ru.itis.loginregistrationapp.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void saveUser(User user);


    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(Long id);

    List<User> findAllUsers();

}
