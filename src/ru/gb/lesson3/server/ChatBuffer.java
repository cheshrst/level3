package ru.gb.lesson3.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatBuffer{




    //  Вывод
//    String doOutput(){
//        File file = new File("./src/ru/gb/lesson3/adapter/history.txt");
//
////        doFileInputStreamDemo(file);
//        return null;
//    }
//
////    char doFileInputStreamDemo(File file) {
////        char msg = 0;
////        try (FileInputStream fin = new FileInputStream(file)) {
////
////            int b;
////            while ((b = fin.read()) != -1) {
////                msg = (char) b;
////                return msg;
////            }
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
////
////    }


    public String getLastNLogLines() {
        File file = new File("./src/ru/gb/lesson3/server/history.txt");
        int nLines = 100;
        StringBuilder s = new StringBuilder();
        try {
            Process p = Runtime.getRuntime().exec("tail -"+nLines+" "+file);
            java.io.BufferedReader input = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
            String line = null;
            //Here we first read the next line into the variable
            //line and then check for the EOF condition, which
            //is the return value of null
            while((line = input.readLine()) != null){
                s.append(line+'\n');
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return s.toString();
    }



//    Запись

    void doFileStreamDemo(String outboundMessage) {
        File file = new File("./src/ru/gb/lesson3/server/history.txt");
        doFileOutputStreamDemo(file, outboundMessage);
    }
    void doFileOutputStreamDemo(File file,String outboundMessage) {
        try (FileOutputStream fout = new FileOutputStream(file, true)) {
            fout.write(outboundMessage.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
