package ru.gb.lesson3.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {

    private final ServerSocket socket;
    private final AuthenticationService authenticationService;
    private final Set<ClientHandler> loggedClients;


    public ChatServer() {
        try {
            authenticationService = new AuthenticationService();
            loggedClients = new HashSet<>();
            this.socket = new ServerSocket(8888);

            while (true) {

            System.out.println("Waiting new connection.");
            Socket client = socket.accept();
            System.out.println("Client join.");
            System.out.println("Address: " + client.getInetAddress());
                new Thread(() -> new ClientHandler(client,this)).start();
            }
        }catch (IOException e){
            throw new RuntimeException("Smth went wrong...",e);

        }
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }
    public void addClient(ClientHandler client){
        loggedClients.add(client);
    }
    public void removeUsername(ClientHandler client){
        loggedClients.remove(client);
    }

//Проверка онлайн логина
    public boolean isUsernameOccupied(String username){
        return loggedClients.stream()
                .anyMatch(c -> c.getName().equals(username));
    }


//    public Set<ClientHandler> getLoggedClients() {
//        return loggedClients;
//    }
//
    public void broadcastMessage(String message){
        loggedClients.forEach((ClientHandler ch) -> {
            ch.sendMessage(message);
        });
    }


    public void wisperMsg(Object from, String to, String msg) {
        for (ClientHandler client : loggedClients) {
            if (client.getName().equals(to) && isUsernameOccupied(to)) {
                client.sendMessage("[Msg from: " + from + "] " + msg);
                break;
            }else{
            }
        }
    }
}
