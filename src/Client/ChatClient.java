package Client;

import java.io.*;
import java.net.*;

public class ChatClient {
    private ReadThread rt;
    private WriteThread wt;
    private Socket soc;

    private String username;

    private boolean connected = false;

    public ChatClient(){
        try{
            System.out.println("Client starting...");

            // connect to server
            System.out.println("Attempting to connect to server...");
            soc = new Socket("localhost", 9806);
            System.out.println("Connection successful!");

            // start read write threads
            System.out.println("Starting Read and Write Threads...");
            rt = new ReadThread(soc, this);
            wt = new WriteThread(soc, this);

            rt.start();
            wt.start();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Connection failed...");
            System.out.println("Exiting...");
            System.exit(1);
        }
    }


    public void stop(){
        System.out.println("Exiting...");
        System.exit(0);
    }

    // getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public static void main(String[] args) throws IOException {
        ChatClient client = new ChatClient();
    }
}
