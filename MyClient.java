package assignment5;

import java.io.*;
import java.net.*;
import java.util.*;

public class MyClient {

    public static void main(String[] args) {

        try {
            Socket s = new Socket("localhost", 6666);
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            DataInputStream dis = new DataInputStream(s.getInputStream());
            Scanner sc = new Scanner(System.in);
            outputToServer message = new outputToServer(dos, sc);
            readFromServer read = new readFromServer(dis, dos, s, sc);
            Thread output = new Thread(message);
            Thread input = new Thread(read);

            output.start();
            input.start();

            while (input.isAlive()) {

            }

            message.endThread(true);


        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class outputToServer implements Runnable {
    private boolean endThread = false;
    private DataOutputStream dos;
    private Scanner sc;

    public outputToServer(DataOutputStream dos, Scanner in) {
        this.dos = dos;
        this.sc = in;

    }

    public void run() {
        while (!Game.endGameForAll) {
            try {
                if (endThread) {
                    break;
                }
                String msg = sc.nextLine(); //get input from System.in
                dos.writeUTF(msg); //write message to server

            } catch (IOException e) {System.out.println(e);}

        }
    }

    public void endThread(boolean status) {
        endThread = status;
    }
}

class readFromServer implements Runnable {
    DataOutputStream dos;
    DataInputStream dis;
    Socket s;
    Scanner sc;

    public readFromServer(DataInputStream dis, DataOutputStream dos, Socket s, Scanner sc) {
        this.dis = dis;
        this.dos = dos;
        this.s = s;
        this.sc = sc;
    }

    public void run() {
        while (!Game.endGameForAll) {
            try {
                // read the message sent to this client
                String msg = dis.readUTF();
                System.out.println(msg);
                if (msg.equals("Thank you for playing. Please restart the server to play again.")) {
                    dis.close();
                    dos.close();
                    sc.close();
                    s.close();
                    break;
                }

            } catch (IOException e) {System.out.println(e);}
        }
        try {
            if (Game.endGameForAll) {
                dos.writeUTF("Thank you for playing. Please restart the server to play again.");
                dos.flush();
                dis.close();
                dos.close();
            }
            dis.close();
        } catch (Exception e) {System.out.println(e);}

    }
}


