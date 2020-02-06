package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ChatRoom {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private int maxConnect;
    private int connected;

    private ArrayList<ChatServerThread> cst = new ArrayList<ChatServerThread>();

    public ChatRoom(int maxUsers){
        maxConnect = maxUsers;
        connected = 0;
    }

    public void addUser(Socket soc) throws IOException {
        if(connected < maxConnect) { // if room is not full yet
            ChatServerThread newUser = new ChatServerThread(soc, this);
            cst.add(newUser);
            cst.get(connected).start();
            connected++;
        }
        else{ // if room is full reject user
            PrintWriter out = new PrintWriter(soc.getOutputStream(), true);
            out.println("Room Full");
        }
    }

    public void removeUser(ChatServerThread user){
        for(int i = 0; i < cst.size(); i++){
            if(cst.get(i) == user){
                serverBroadcast(cst.get(i).getUsername() + " has disconnected");
                cst.get(i).closeSoc();
                cst.remove(i);
                connected--;
            }
        }
    }

    public void serverBroadcast(String msg){
        // send to all clients
        for(int i = 0; i < cst.size(); i++){
            cst.get(i).serverToClient(ANSI_GREEN + "[Server]: " + msg + ANSI_RESET);
            System.out.println(ANSI_GREEN + "[Server]: " + msg + ANSI_RESET);
        }
    }

    public void broadcast(String msg, ChatServerThread sender){
        // send to all clients except the msg origin
        for(int i = 0; i < cst.size(); i++){
            if(cst.get(i) != sender){
                cst.get(i).sendToClient(ANSI_BLUE + "[" + sender.getUsername() + "]: " + ANSI_RESET + msg, sender.getUsername());
                System.out.println(ANSI_BLUE + "[" + sender.getUsername() + "]: " + ANSI_RESET + msg);
            }
        }
    }

}
