package ru.gb.lesson2.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnector {
    // sudo docker run --name gb-mysql -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:latest
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "root");
        } catch (SQLException e) {
            throw new RuntimeException("SWW during getting a connection.", e);
        }//void
    }
    public static void close(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("SWW during closing a connection.", e);
        }//void
    }
    public static void rollback(Connection connection){
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("SWW during rollback.", e);
        }//void
    }
}
