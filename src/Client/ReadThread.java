package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread {
    // text colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    Socket soc;

    ChatClient client;

    BufferedReader in; // receives data from server

    public ReadThread(Socket soc, ChatClient client) throws IOException {
        this.soc = soc;
        this.client = client;

        in = new BufferedReader(new InputStreamReader(this.soc.getInputStream()));
    }

    public void run(){
        System.out.println("Read thread running...");
        while(true){
            try {
                String msg = in.readLine();

                if(msg.equals("connected")) {
                    client.setConnected(true);
                }
                else if(!msg.equals("received")) {
                    System.out.println(msg); // prints anything the server sends to terminal
                }

                if(msg.equals("Room Full")){ // if room is full terminate
                    client.stop();
                }

                if(client.isConnected()) {
                    System.out.print(ANSI_BLUE + "[" + client.getUsername() + "]: " + ANSI_RESET); // print username after receiving msg
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
