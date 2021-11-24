package ru.gb.lesson2;


import ru.gb.lesson2.server.ChatServer;
import ru.gb.lesson2.server.CustomerService;

public class ServerStarter {

    public static void main(String[] args){
        CustomerService customerService = new CustomerService();
        System.out.println(customerService.findAll());
        new ChatServer();
    }
}
