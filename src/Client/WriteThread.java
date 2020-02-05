package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WriteThread extends Thread{
    Socket soc;
    ChatClient client;

    BufferedReader in; // user input
    PrintWriter write; // send to server

    public WriteThread(Socket soc, ChatClient client) throws IOException {
        this.soc = soc;
        this.client = client;

        in = new BufferedReader(new InputStreamReader(System.in));
        write = new PrintWriter(this.soc.getOutputStream(), true);
    }

    public void run(){
        System.out.println("Write Thread running...");

        // sends server username
        try {
            client.setUsername(in.readLine());
            write.println(client.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
        }


        while(true){
            try {
                write.println(in.readLine()); // sends user text to server
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
