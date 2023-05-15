package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {

        User user1 = new User("Misha", "Petrov", (byte) 20);
        User user2 = new User("Bato", "Ebloev", (byte) 25);
        User user3 = new User("Rulon", "Oboev", (byte) 31);
        User user4 = new User("Tregul", "Zaurov", (byte) 38);

        UserService userDaoHibernate = new UserServiceImpl();

        userDaoHibernate.dropUsersTable();
        userDaoHibernate.createUsersTable();
        userDaoHibernate.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        System.out.println("User with name - " + user1.getName() + " " + user1.getLastName() + " is added to the database");

        userDaoHibernate.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        System.out.println("User with name - " + user2.getName() + " " + user2.getLastName() + " added to database");

        userDaoHibernate.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        System.out.println("User with name - " + user3.getName() + " " + user3.getLastName() + " added to database");

        userDaoHibernate.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
        System.out.println("User with name - " + user4.getName() + " " + user4.getLastName() + " added to database");

        for (int i = 0; i < userDaoHibernate.getAllUsers().size(); i++) {
            System.out.println(userDaoHibernate.getAllUsers().get(i));
        }

        userDaoHibernate.cleanUsersTable();
        userDaoHibernate.dropUsersTable();

        Util.closeFactory();

    }
}
