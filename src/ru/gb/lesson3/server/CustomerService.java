package ru.gb.lesson3.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    public List<Customer> findAll(){
        Connection connection = DatabaseConnector.getConnection();


        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM customers");

            List<Customer> customers = new ArrayList<>();

            while (rs.next()){
                customers.add(
                        new Customer(
                                rs.getLong("id"),
                                rs.getString("login"),
                                rs.getString("username"),
                                rs.getString("pass")
                        )

                );
            }

            return customers;

        } catch (SQLException e) {
            throw new RuntimeException("SWW during a fetching operation", e);
        }finally {
            DatabaseConnector.close(connection);
        }
    }



    public void save(Customer customer){
        Connection connection = DatabaseConnector.getConnection();

        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO customers (login, username, pass) VALUES (?, ?, ?)"
            );
            preparedStatement.setString(1, customer.login());
            preparedStatement.setString(2, customer.username());
            preparedStatement.setString(3, customer.pass());


            preparedStatement.executeUpdate();
            connection.commit();
            } catch (SQLException e) {
            DatabaseConnector.rollback(connection);
            throw new RuntimeException("SWW during a fetching operation", e);
        }finally {
            DatabaseConnector.close(connection);
        }

    }

    public void updateUsername(Customer customer){
        Connection connection = DatabaseConnector.getConnection();

        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE customers SET username WHERE id=? && pass=?"
            );
            preparedStatement.setString(1, customer.login());
            preparedStatement.setString(2, customer.username());
            preparedStatement.setString(3, customer.pass());


            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            DatabaseConnector.rollback(connection);
            throw new RuntimeException("SWW during a fetching operation", e);
        }finally {
            DatabaseConnector.close(connection);
        }

    }

    public void update(Customer customer){
        Connection connection = DatabaseConnector.getConnection();

        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE customers SET username = ? WHERE login = ? && pass = ?"
            );
            preparedStatement.setString(1, customer.username());
            preparedStatement.setString(2,customer.login());
            preparedStatement.setString(3, customer.pass());

            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            DatabaseConnector.rollback(connection);
            throw new RuntimeException("SWW during a fetching operation", e);
        }finally {
            DatabaseConnector.close(connection);
        }

    }

    public void delete(long id){
        Connection connection = DatabaseConnector.getConnection();

        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM customers WHERE id = ?"
            );
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            DatabaseConnector.rollback(connection);
            throw new RuntimeException("SWW during a fetching operation", e);
        }finally {
            DatabaseConnector.close(connection);
        }


    }
}
