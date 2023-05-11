package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

//    private final UserDao userDao = new UserDaoJDBCImpl();
    private final UserDao userDao = new UserDaoHibernateImpl();

    //     создаем таблицу пользователей
    public void createUsersTable() {
        userDao.createUsersTable();
    }

    //    удаление таблицы
    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    //    добавление пользователя в таблицу
    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
    }

    //    удаление пользователя по ID
    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    //    сохранение пользователя/удаление или создание таблицы
    public List<User> getAllUsers() throws SQLException {
        return userDao.getAllUsers();
    }

    //    очищение таблицы пользователей
    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
