package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();
    public UserDaoHibernateImpl() {
    }

    // 1. Создание таблицы
    @Override
    public void createUsersTable() {
        try  (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session
                    .createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                            "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                            "name VARCHAR(255) NOT NULL, lastName VARCHAR(255) NOT NULL, " +
                            "age TINYINT NOT NULL)")
                    .addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        }
    }
    // 2. Удаление таблицы
    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        }
    }
    // 4. Добавление Юзера в таблицу
    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            tx1.commit();
        }
    }
    // 5. Удаление Юзера из таблицы
    @Override
    public void removeUserById(long id) {

        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            User user = new User();
            session.remove(user);
            tx1.commit();
        }
    }
    // 6. Получение всех Юзеров
    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User", User.class)
                    .getResultList();
        }
    }
    // 3. Очистка таблицы
    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}