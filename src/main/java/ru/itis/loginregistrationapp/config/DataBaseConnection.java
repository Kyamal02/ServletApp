package ru.itis.loginregistrationapp.config;

import com.oracle.wls.shaded.org.apache.bcel.util.ClassLoader;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class DataBaseConnection {
    private final HikariDataSource ds;

    public DataBaseConnection() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.user"));
        config.setPassword(properties.getProperty("db.password"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(30000); // 30 секунд
        config.setMaxLifetime(1200000); // 20 минут
        this.ds = new HikariDataSource(config);
    }

    static {
        try {
            ClassLoader classLoader = new ClassLoader();

            //Выполнит статический блок в драйвере, который инициализирует его в DriverManager
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public void closeDataSource() {
        ds.close();
    }
}
