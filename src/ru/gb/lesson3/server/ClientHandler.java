package ru.gb.lesson3.server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
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
        start();
    }



ChatBuffer buffer = new ChatBuffer();


    public String getName() {
        return name;
    }

    public void start(){
        while (true){
            doAuthication();
//            lastMsgtc();
            getLastNLogLines();
            commands();
        }
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


    public void getLastNLogLines() {
        File file = new File("./src/ru/gb/lesson3/server/history.txt");
        int nLines = 100;
        StringBuilder s = new StringBuilder();
        try {
            Process p = Runtime.getRuntime().exec("tail -"+nLines+" "+file);
            java.io.BufferedReader input = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
            String line = null;

            while((line = input.readLine()) != null){
                s.append(line+'\n');
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        sendMessage(s.toString());
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
                }else if (inboundMessage.startsWith("-logout")){
                    sendMessage("Logged out!");
                    server.broadcastMessage("[" + this.getName() + "] logged out.");
                    server.removeUsername(this);
                    break;
                }
                else if(inboundMessage.startsWith("-exit")){
                    sendMessage("Disconnect..");
                    this.socket.close();
                    server.removeUsername(this);
                }else if(inboundMessage.startsWith("-nn")){
                    String[] credentials = inboundMessage.split("\\s");
                    String login = credentials[1];
                    String newUsername = credentials[2];
                    String pass = credentials[3];
                    changeUsername(login, newUsername, pass);
                } else {
                    buffer.doFileStreamDemo(this.getName() + ": " + inboundMessage + "\n");
                    server.broadcastMessage(this.getName() + ": " + inboundMessage);
                }
            }
        }



    private void timeout(int time){
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                sendMessage("Timeouted");
//            }
//        };
//        Timer timer = new Timer("Timer");
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
                "[-logout] logout from server" +
                "[-exit] disconnect from server");

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

//    private void lastMsg() throws IOException {
//        File file = new File("./src/ru/gb/lesson3/server/history.txt");
//        BufferedReader input = new BufferedReader(new FileReader(file));
//        try (FileInputStream fin = new FileInputStream(file)) {
//            int b;
//            ArrayList msg = new ArrayList();
//
//
//            while ((b = fin.read()) != -1) {
//                msg.add((char) b);
//            }
//            String s = msg.toString().replaceAll("[,\\s\\[\\]]", "").replaceAll("//endmsg///", "\n");
//            sendMessage(s);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
    //    private void lastMsgtc(){
//        try {
//            lastMsg();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}