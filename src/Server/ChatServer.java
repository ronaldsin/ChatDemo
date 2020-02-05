package Server;

import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) {
        System.out.println("Starting server...");

        int maxConnect = 5; // max users

        ServerSocket ss = null;

        ChatRoom cr = new ChatRoom(maxConnect);

        System.out.println("Creating server socket with port 9806...");
        try {
            ss = new ServerSocket(9806); // create server socket
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server Socket creation failed.");
        }

        while (true) { // keep waiting for new connections
            System.out.println("Waiting for client...");
            try {
                Socket soc = ss.accept();
                System.out.println("Connection established");
                cr.addUser(soc);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Connection error");
            }
        }
    }
}
