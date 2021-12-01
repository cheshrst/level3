package ru.gb.lesson4.chat.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnector {
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "root");
        } catch (SQLException e) {
            throw new RuntimeException("SWW during getting a connection.", e);
        }
    }
    public static void close(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("SWW during closing a connection.", e);
        }
    }
    public static void rollback(Connection connection){
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("SWW during rollback.", e);
        }
    }
}
