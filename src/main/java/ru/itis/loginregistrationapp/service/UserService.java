package ru.itis.loginregistrationapp.service;

import org.mindrot.jbcrypt.BCrypt;
import ru.itis.loginregistrationapp.models.User;
import ru.itis.loginregistrationapp.exception.PasswordMismatchException;
import ru.itis.loginregistrationapp.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(String email, String password, String rePassword) {
        if (!password.equals(rePassword)) {
            throw new PasswordMismatchException("Пароли не должны совпадать");
        } else {
            String salt = BCrypt.gensalt(12);
            String password_hash = BCrypt.hashpw(password, salt);
            User user = new User(email, password_hash);
            userRepository.saveUser(user);
        }
    }

    public Optional<User> loginUser(String email, String password) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.filter(user -> BCrypt.checkpw(password, user.getPassword()));
        }
        return Optional.empty();
    }

    public List<User> getAllUsersExceptCurrent(String currentUserEmail) {
        return userRepository.findAllUsers().stream().filter(user ->
                !user.getEmail().equals(currentUserEmail)).collect(Collectors.toList());

    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findUserById(userId);
    }
}
