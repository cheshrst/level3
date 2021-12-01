package ru.gb.lesson4.chat;


import ru.gb.lesson4.chat.server.ChatServer;
import ru.gb.lesson4.chat.server.CustomerService;

public class ServerStarter {

    public static void main(String[] args){
        CustomerService customerService = new CustomerService();
        System.out.println(customerService.findAll());
        new ChatServer();
    }
}

/** sudo docker run --name gb-mysql -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:latest
 * sudo docker start gb-mysql
 */