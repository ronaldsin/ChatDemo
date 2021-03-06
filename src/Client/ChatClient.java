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
        System.out.println("Client starting...");
        do {
            try {
                // connect to server
                System.out.println("Attempting to connect to server...");
                soc = new Socket("localhost", 25565);
                System.out.println("Connection successful!");
                break;

            } catch (Exception e) {
                System.err.println("Connection failed...");

                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

                    System.out.println("Press enter to try again...");

                    in.readLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }while(true);


        // start read write threads
        System.out.println("Starting Read and Write threads...");

        try {
            rt = new ReadThread(soc, this);
            wt = new WriteThread(soc, this);

            rt.start();
            wt.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to start Read and Write threads...");
            System.out.println("Exiting...");
            System.exit(1);
        }
    }


    public void stop() throws IOException {
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
