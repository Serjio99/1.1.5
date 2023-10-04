package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private Connection connection = getConnection();

    public UserDaoJDBCImpl() {
    }

    // 1. Создание таблицы +:
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS mysql1" +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age INT)");
            System.out.println("Создание таблицы - ОК");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Создание таблицы - NO ОК");
        }
    }

    // 2. Удаление таблицы +:
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS mysql1");
        } catch (SQLException e) {
            System.out.println("Удаление таблицы - OK");
            e.printStackTrace();
            System.out.println("Добавление в таблицу ПРОБЛЕМА");
        }
    }

    // 3. Очистка содержания таблицы +:
    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE mysql1");
            System.out.println("Удаление User из таблицы - OK");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Удаление User из таблицы - NO OK");
        }
    }

    // 4. Добавление Юзера в таблицу -:
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO mysql1 (name, " +
                "last_name, age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("Добавление Юзера - ОК");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Добавление Юзера - NO OK");
        }
    }

    //  5. Удаление Юзера по id +:
    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM mysql1 WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Переименование ОК");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Переименование Проблема");
        }
    }

    // 6. Получение всех Юзеров из таблицы -:
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM mysql1")) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("last_name"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
                System.out.println("Получение всех User - OK");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Получение всех User - NO OK");
        }
        return users;
    }
}