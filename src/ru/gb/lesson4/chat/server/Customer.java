package ru.gb.lesson4.chat.server;

public record Customer (long id,String login, String username, String pass, boolean admin) {


    public Customer(long id, String login, String username, String pass, boolean admin) {
        this.id = id;
        this.login = login;
        this.username = username;
        this.pass = pass;
        this.admin = admin;
    }
    public Customer(String login, String username, String pass) {
        this(0, login, username, pass, false);
    }


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return pass;
    }
    public String getUsername() {
        return username;
    }
}
