package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory factory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    // создание таблицы пользователей
    @Override
    public void createUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE USER(id INTEGER PRIMARY KEY not NULL AUTO_INCREMENT,name VARCHAR(45), lastName VARCHAR (50), age INT not NULL)").executeUpdate();
            System.out.println("Таблица создана");
        } catch (Exception e) {
            System.out.println("Таблица уже существует");
        }
    }

    // удаление таблицы
    @Override
    public void dropUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE USER").executeUpdate();
        } catch (Exception e) {
            System.out.println("Такой таблицы не существует");
        }
    }

    //  добавление в базу данных
    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException throwables) {
            throwables.printStackTrace();
        }
    }

    //  удаление пользователя по id
    @Override
    public void removeUserById(long id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (HibernateException throwables) {
            throwables.printStackTrace();
        }
    }

    //  сохранение всех пользователей таблицы
    @Override
    public List<User> getAllUsers() {
        List<User> user = new ArrayList<>();
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            user = session.createQuery("FROM User", User.class).list();
            session.getTransaction().commit();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    //    очищение таблицы пользователей
    @Override
    public void cleanUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE User").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException throwables) {
            throwables.printStackTrace();
        }
    }
}
