package Server;

import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args){
        System.out.println("Starting server...");

        int maxConnect = 5; // max users

        ServerSocket ss = null;

        ChatRoom cr = new ChatRoom(maxConnect);

        System.out.println("Creating server socket with port 25565...");
        try {
            ss = new ServerSocket(25565); // create server socket
            ss.setSoTimeout(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server Socket creation failed.");
        }

        try {
            while (true) { // keep waiting for new connections
                System.out.println("Waiting for client...");
                try {
                    Socket soc = ss.accept();
                    System.out.println("Connection established");
                    cr.addUser(soc);
                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
                    System.out.println("Connection error");
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
