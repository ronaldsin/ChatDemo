package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

class ChatServerThread extends Thread{
    private String username;

    private boolean connected = false; // used to ensure user doesnt receive msgs untill they have a username

    private PrintWriter out;
    private BufferedReader in;

    private Socket soc;
    private ChatRoom cr;

    public ChatServerThread(Socket soc, ChatRoom cr) throws IOException {
        System.out.println("Starting user chat thread...");
        this.soc = soc;
        this.cr = cr;

         in = new BufferedReader(new InputStreamReader(this.soc.getInputStream()));
         out = new PrintWriter(this.soc.getOutputStream(), true);
    }

    public void run(){
        System.out.println("Chat thread running...");

        // wait for client to send username
        try {
            serverToClient(ChatRoom.ANSI_GREEN + "[Server]: You have connected to the chat..." + ChatRoom.ANSI_RESET);
            serverToClient(ChatRoom.ANSI_GREEN + "[Server]: Please enter a username:" + ChatRoom.ANSI_RESET);

            username = in.readLine();

            serverToClient(ChatRoom.ANSI_GREEN + "[Server]: Welcome " + username + " enjoy your stay" + ChatRoom.ANSI_RESET);

            cr.serverBroadcast("[" + username + "] has connected" );

            serverToClient("connected");
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // broadcast to chat when a new msg is received
        while(true){
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
                cr.broadcast(in.readLine(), this);
                serverToClient("received");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendToClient(String msg, String user){
        if(connected) {
            out.println(msg);
        }
    }

    public void serverToClient(String msg){
        out.println(msg);
    }

    public String getUsername(){
        return username;
    }
}

