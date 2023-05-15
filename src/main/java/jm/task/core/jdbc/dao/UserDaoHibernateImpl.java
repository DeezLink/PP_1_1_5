package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
            e.printStackTrace();
            System.out.println("Таблица уже существует");
        }
    }

    // удаление таблицы
    @Override
    public void dropUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE USER").executeUpdate();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Такой таблицы не существует");
        }
    }

    //  добавление в базу данных
    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tr = null;
        try (Session session = factory.openSession()) {
            tr = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            System.out.println("Пользователь с именем " + name + " " + lastName + " добавлен в базу данных");
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
            System.out.println("Не получилось создать пользователя");
        }
    }

    //  удаление пользователя по id
    @Override
    public void removeUserById(long id) {
        Transaction tr = null;
        try (Session session = factory.openSession()) {
            tr = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            System.out.println("Пользователь с ID " + id + " удален из базы данных");
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
            System.out.println("Ошибка удаления пользоваетеля");
        }
    }

    //  сохранение всех пользователей таблицы
    @Override
    public List<User> getAllUsers() {
        Transaction tr = null;
        List<User> user = new ArrayList<>();
        try (Session session = factory.openSession()) {
            tr = session.beginTransaction();
            user = session.createQuery("FROM User", User.class).list();
            System.out.println("Пользователь сохранен в таблице");
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
            System.out.println("Ошибка сохранения пользователей в таблице");
        }
        return user;
    }

    //    очищение таблицы пользователей
    @Override
    public void cleanUsersTable() {
        Transaction tr = null;
        try (Session session = factory.openSession()) {
            tr = session.beginTransaction();
            session.createQuery("DELETE User").executeUpdate();
            System.out.println("Таблица пользователей очищена");
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
            System.out.println("Ошибка очищения таблицы пользователей");
        }
    }
}
