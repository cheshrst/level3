package ru.gb.lesson2;


import ru.gb.lesson2.adapter.ChatAdapter;

public class ChatStarter {
    public static void main(String[] args){
        new ChatAdapter();
    }
}
// sudo docker run --name gb-mysql -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:latest