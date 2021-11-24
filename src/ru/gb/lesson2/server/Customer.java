package ru.gb.lesson2.server;

public record Customer (long id,String login, String username, String pass) {


    public Customer(long id, String login, String username, String pass) {
        this.id = id;
        this.login = login;
        this.username = username;
        this.pass = pass;
    }
    public Customer(String login, String username, String pass) {
        this(0, login, username, pass);
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

/**
class ClassicClassCustomer{
    private final long id;
    private final String username;
    private final String pass;

    public ClassicClassCustomer(long id, String username, String pass) {
        this.id = id;
        this.username = username;
        this.pass = pass;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }
}
*/
