package ru.gb.lesson3.server;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class AuthenticationService {
    static CustomerService customerService = new CustomerService();

    private static final List<Customer> users = customerService.findAll();

//    public void updateInfo(){
//        final List<Customer> users = customerService.findAll();
//
//    }

    public Optional<String> findNickByLoginAndPassword(String login, String password) {



        return users.stream()
                 //try catch?
                .filter(u -> u.getLogin().equals(login) && u.getPassword().equals(password))
                .findFirst()
                .map(Customer::getUsername);
    }



    private static class User{
        private String username;
        private String login;
        private String password;

        public User(String username, String login, String password) {
            this.username = username;
            this.login = login;
            this.password = password;
        }



//        public String getPassword() {
//            return password;
//        }

//        public String getLogin() {
//            return login;
//        }

//        public String getUsername() {
//            return username;
//        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(username, user.username) && Objects.equals(login, user.login) && Objects.equals(password, user.password);
        }

        @Override
        public int hashCode() {
            return Objects.hash(username, login, password);
        }
    }





}
