package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Driver;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.DRIVER;

public class Util {
    // соеденение с БД
    public static Connection connection = null;
    public static final String URL = "jdbc:mysql://localhost:3306"; // путь к БД верный?
    public static final String Driver = "com.mysql.cj.jdbc.Driver";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";
    public static SessionFactory sessionFactory;

    public static Connection getConnection() {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            Class.forName(Driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Подключение успешно!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBS драйвер не нашолся!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL-ошибка");
        }
        return connection;
    }
    // --------------------------------sessionFactory

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();  // ---------------?
                Properties properties = new Properties();
                properties.put(Environment.URL, URL);
                properties.put(DRIVER, DRIVER); // ------------- ?
                properties.put(Environment.USER, USERNAME);
                properties.put(Environment.PASS, PASSWORD);
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect"); // ------------- ?

                properties.put(Environment.SHOW_SQL, "true");

                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("Объект SessionFactory ПОЛУЧЕН");
            } catch (Exception e) {
                System.out.println("Объект SessionFactory НЕ ПОЛУЧЕН");
            }
        }
        return sessionFactory;
    }

}
