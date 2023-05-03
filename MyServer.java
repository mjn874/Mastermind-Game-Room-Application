package assignment5;

import java.io.*;
import java.net.*;
import java.util.*;

public class MyServer {

    //made all static bc tried to pass parameters to game w/ 'this' keyword but main was not static and could not run server unless main was static
    private static final String secretCode = SecretCodeGenerator.getInstance().getNewSecretCode();;

   public static void main(String[] args) throws IOException {
       try{
           ServerSocket ss=new ServerSocket(6666);
           while(true){
               try{
                   Socket s =ss.accept();//establishes connection
                   System.out.println("A new player has joined the game!");
                   DataInputStream dis = new DataInputStream(s.getInputStream());
                   DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                   new Thread(new MyClientDriver(secretCode, s, dis, dos)).start();
               }catch (IOException e){System.out.println(e);}
           }
       }catch(Exception e){System.out.println(e);}
    }
}
