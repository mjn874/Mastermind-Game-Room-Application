package assignment5;
import java.net.*;
import java.io.*;

public class MyClientDriver implements Runnable {
    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket s;
    private String secretCode;


    public MyClientDriver(String secretCode, Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.secretCode = secretCode;
    }

    public void greetUser() {
        try {
            dos.writeUTF("Welcome to Mastermind.  Here are the rules.\n" +
                    "This is a text version of the classic board game Mastermind. \n" +
                    "The  computer  will  think  of  a  secret  code.  The  code  consists  of  4  \n" +
                    "colored  pegs.  The  pegs  MUST  be  one  of  six  colors:  blue,  green,  \n" +
                    "orange, purple, red, or yellow. A color may appear more than once in  \n" +
                    "the  code.  You  try  to  guess  what  colored  pegs  are  in  the  code  and  \n" +
                    "what  order  they  are  in.  After  you  make  a  valid  guess  the  result  \n" +
                    "(feedback) will be displayed. \n\n" +
                    "The result  consists  of  a  black  peg  for  each  peg  you  have  guessed \n" +
                    "exactly correct (color and position) in your guess.  For each peg in  \n" +
                    "the guess that is the correct color, but is out of position, you get  \n" +
                    "a  white  peg.  For  each  peg,  which  is  fully  incorrect,  you  get  no  \n" +
                    "feedback.  \n" +
                    "Only the first letter of the color is displayed. B for Blue, R for \n" +
                    "Red, and so forth. When entering guesses you only need to enter the \n" +
                    "first character of each color as a capital letter. \n\n"
            );
            dos.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void isClientReady() {
        try {
            dos.writeUTF("You have " + GameConfiguration.guessNumber + " guesses to  figure  out  the  secret  code  or  you  lose  the  +\n" +
                    "game.  Are you ready to play? (Y/N):  ");
            dos.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        try {
            greetUser();
            Game userGame = new Game(secretCode, GameConfiguration.guessNumber, dis, dos);
            isClientReady();
            String decision = dis.readUTF();

            if (!decision.equals("Y")) {
                dos.writeUTF("Thank you for playing. Please restart the server to play again.");
                dis.close();
                dos.close();
                userGame.setPlay(false);
                s.close();

            }
            while (userGame.continuePlaying() && !Game.endGameForAll) {
                if (!userGame.gameOver)
                    userGame.run();
                else {
                    dos.writeUTF("Would you like to try again (Y/N): ");
                    decision = dis.readUTF();
                    if (!decision.equals("Y")) {
                        dos.writeUTF("Thank you for playing. Please restart the server to play again.");
                        dos.close();
                        dis.close();
                        userGame.setPlay(false);
                        s.close();
                        break;
                    }
                    userGame.newGame(secretCode, GameConfiguration.guessNumber);
                }

            }
            dis.close();
            dos.close();
            s.close();

        } catch (Exception e) {System.out.println(e);}

    }

}
