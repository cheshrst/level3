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

/** Сделан воид start(). Необходим для корректного выполнения logout, для корректной возможности повторного входа
Необходимо теперь вводить пароль при смене ника (Почему ранее не сделал ????!?)))



 */