package ru.gb.lesson2.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientHandler {
    private String name;
    private final Socket socket;
    private final ChatServer server;
    private final DataInputStream in;
    private final DataOutputStream out;

    public ClientHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            throw new RuntimeException("Smth went wrong ", ex);
        }
        doAuthication();
        commands();
    }



    public String getName() {
        return name;
    }

    private void doAuthication(){
        try {
            timeout(12);
            performAuthentication();
        }catch (IOException ex){
            throw new RuntimeException("Smth went wrong during a client authetication", ex);
        }
    }
    private void commands(){
        try {
            commandsWithMsg();
        }catch (IOException ex){
            server.broadcastMessage("[" + this.getName() + "] leave from server.");
            server.removeUsername(this);
            throw new RuntimeException("Smth went wrong with commands", ex);
        }
    }


        private void commandsWithMsg() throws IOException {

        while (true) {
                String inboundMessage = in.readUTF();
                if (inboundMessage.startsWith("-pm")) {
                    String[] credentials = inboundMessage.split("\\s");
                    String[] message = inboundMessage.split(credentials[1]);
                    String msg = message[1];
                    String client = credentials[1];
                    if(server.isUsernameOccupied(client)){
                        server.wisperMsg(this.getName(), client, msg);
                        sendMessage("[Msg to: " + client + "] " + msg);
                    }else{
                        sendMessage("Client not found");}
                }else if(inboundMessage.startsWith("-help")){
                    help();
                }else if(inboundMessage.startsWith("-exit")){
                    sendMessage("Logged out!");
                    server.removeUsername(this);
                }else if(inboundMessage.startsWith("-nn")){
                    String[] credentials = inboundMessage.split("\\s");
                    String login = credentials[1];
                    String newUsername = credentials[2];
                    String pass = credentials[3];
                    changeUsername(login, newUsername, pass);
                } else {
                    server.broadcastMessage(this.getName() + ": " + inboundMessage);
                }
            }
        }



    private void timeout(int time){
        try {
            this.socket.setSoTimeout(1000 * time); // 0 - infinity
        } catch (SocketException ex) {
            throw new RuntimeException("Smth went wrong with timeout", ex);
        }
    }

    public void sendMessage(String outboundMessage){
        try {
            out.writeUTF(outboundMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void help(){
        sendMessage("[-pm] private message\n" +
                "[-nn] change nickname (login, new nickname, pass)\n" +
                "[-exit] logout from server");

    }



    private void performAuthentication() throws IOException{
        sendMessage("Welcome! \nWrite -auth to start chatting! \n" +
                "or write -reg to register (Example: -reg username nickname password )");
        while (true){
            String inboundMessage = in.readUTF();
            if(inboundMessage.startsWith("-auth")){
                String[] credentials = inboundMessage.split("\\s");
                AtomicBoolean isSuccess = new AtomicBoolean(false);
                server.getAuthenticationService()
                        .findNickByLoginAndPassword(credentials[1], credentials[2])
                        .ifPresentOrElse(
                                username -> {
                                    if (!server.isUsernameOccupied(username)) {
                                        timeout(0);
                                        server.broadcastMessage(String.format("User[%s] is logged in", username));
                                        name = username;
                                        server.addClient(this);
                                        isSuccess.set(true);
                                        sendMessage("Auth done! Enjoy :)\nWrite -help to read commands");
                                    } else {
                                        sendMessage("Current username is already occupied. \nTry later...");
                                    }
                                },
                                () -> sendMessage("Bad credentials.")
                        );

                if(isSuccess.get())break;
            }else if(inboundMessage.startsWith("-reg")){
                CustomerService customerService = new CustomerService();
                String[] credentials = inboundMessage.split("\\s");
                Customer customer = new Customer(credentials[1], credentials[2], credentials[3]);
                customerService.save(customer);
                customerService.findAll();
                sendMessage("Done! Now write -auth to start chatting");
            }
            else{
                sendMessage("You need to be logged.");
            }
        }
    }

    public void changeUsername(String login, String username, String pass){
        CustomerService customerService = new CustomerService();
        Customer customer = new Customer(login, username, pass);
        customerService.update(customer);
        sendMessage("username will be change after reboot server");
    }
//    public void changePass(String login, String pass){
//        CustomerService customerService = new CustomerService();
//        Customer customer = new Customer(login, username, pass);
//        customerService.update(customer);
//        sendMessage("username will be change after reboot server");
//    }

}