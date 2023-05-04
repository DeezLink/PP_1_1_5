package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();
//  Создание таблицы user
        userService.createUsersTable();

//  Добавление 4 user в таблицу
        userService.saveUser("Misha", "Petrov", (byte) 20);
        userService.saveUser("Bato", "Ebloev", (byte) 25);
        userService.saveUser("Rulon", "Oboev", (byte) 31);
        userService.saveUser("Tregul", "Zaurov", (byte) 38);

//  Вывод всех юзеров из таблицы в консоль
        System.out.println(userService.getAllUsers().toString());
//  Удаление юзера по ID
        userService.removeUserById(1);
//  Очистка таблицы
        userService.cleanUsersTable();
//  Удаление таблицы из БД
        userService.dropUsersTable();
    }
}
