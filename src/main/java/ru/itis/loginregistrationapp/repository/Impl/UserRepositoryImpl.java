package ru.itis.loginregistrationapp.repository.Impl;


import ru.itis.loginregistrationapp.models.User;
import ru.itis.loginregistrationapp.config.DataBaseConnection;
import ru.itis.loginregistrationapp.repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserRepositoryImpl implements UserRepository {
    private final String saveUserQuery = "insert into user_data  (email, password_hash)values (?, ?);";
    private final String findUserByEmail = "select * from user_data where email = ?;";
    private final String findUserById = "select * from user_data where id = ?;";
    private final String findAllUsers = "select * from user_data";


    private final DataBaseConnection ds;

    public UserRepositoryImpl(DataBaseConnection dataBaseConnection) {
        this.ds = dataBaseConnection;
    }

    @Override
    public void saveUser(User user) {
        try (Connection connection = ds.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(saveUserQuery);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findUserByEmail)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(User.getObjectFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске пользователя по email", e);
        }
    }

    @Override
    public Optional<User> findUserById(Long id) {
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findUserById)
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(User.getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllUsers)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            User user;
            while (resultSet.next()) {
                user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password_hash"));
                users.add(user);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }


}
