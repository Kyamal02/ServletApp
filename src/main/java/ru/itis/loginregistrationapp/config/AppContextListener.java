package ru.itis.loginregistrationapp.config;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import ru.itis.loginregistrationapp.repository.Impl.UserRepositoryImpl;
import ru.itis.loginregistrationapp.repository.UserRepository;
import ru.itis.loginregistrationapp.service.UserService;

public class AppContextListener implements ServletContextListener {
    private DataBaseConnection dbConnection;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Создаем зависимости
        dbConnection = new DataBaseConnection();
        UserRepository userRepository = new UserRepositoryImpl(dbConnection);
        UserService userService = new UserService(userRepository);

        // Сохраняем UserService в ServletContext
        ServletContext context = sce.getServletContext();
        context.setAttribute("userService", userService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (dbConnection != null) {
            dbConnection.closeDataSource();
        }
    }
}