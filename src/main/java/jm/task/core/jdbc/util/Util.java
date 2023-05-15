package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // настройка соеденения с БД

    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/mydb114";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "root";

    public static Connection getConnection() {


        Connection connection = null;

        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            System.out.println("Connection OK");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.err.println("Connection ERROR");
        }
        try {
            assert connection != null;
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    public void close() {
        try {
            getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static SessionFactory factory;

    public static SessionFactory getSessionFactory() {

        try {
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
            properties.put(Environment.URL, "jdbc:mysql://localhost:3306/mydb114?serverTimezone=Europe/Moscow&useSSL=false");
            properties.put(Environment.USER, "root");
            properties.put(Environment.PASS, "root");
            properties.put(Environment.FORMAT_SQL, "true");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            properties.put(Environment.HBM2DDL_AUTO, "");
            properties.put(Environment.POOL_SIZE, "5");

            factory = new Configuration()
                    .setProperties(properties)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("build SessionFactory failed :" + ex);
            throw new ExceptionInInitializerError(ex);
        }
        return factory;
    }

    public static void closeFactory() {
        getSessionFactory().close();
    }
}