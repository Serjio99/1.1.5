package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        Util.getConnection();
        UserService userService = new UserServiceImpl();

        userService.createUsersTable(); // Создание таблицы

        userService.saveUser("Имя1", "Фамилия1", (byte) 20); // Добавляем 4х Юзеров
        userService.saveUser("Имя2", "Фамилия2", (byte) 25);
        userService.saveUser("Имя3", "Фамилия3", (byte) 30);
        userService.saveUser("Имя4", "Фамилия4", (byte) 35);

        //userService.removeUserById(1); // Удалить Юзера
        userService.getAllUsers(); // Получение всех Юзеров
        userService.cleanUsersTable(); // Очистка таблицы
        userService.dropUsersTable(); // Удаление таблицы
    }
}