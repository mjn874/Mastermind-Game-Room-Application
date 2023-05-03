/* EE422C Assignment #2 submission by
 * Replace <...> with your actual data.
 * Milton Nava
 * mjn874
 */
package assignment5;
import java.io.*;
import java.lang.*;
import java.util.*;


public class Game {
    DataInputStream dis;
    DataOutputStream dos;
    private int numGuesses;
    private database userHistory;
    static String secretCode;
    static HashSet<String> colors;

    static Boolean endGameForAll = false;
    public boolean gameOver;
    private boolean play;

    public boolean continuePlaying() {
        return play;
    }

    public void setPlay(boolean a) {
        play = a;
    }

    public Game( String secretCode, int numGuesses, DataInputStream dis, DataOutputStream dos) {

        this.numGuesses = numGuesses;
        this.dis = dis;
        this.dos = dos;
        Game.secretCode = secretCode;
        colors = new HashSet<>();
        userHistory = new database(dos);
        gameOver = false;
        play = true;

        Collections.addAll(colors, GameConfiguration.colors);

    }

    public void run() throws Exception {
        try {
            dos.writeUTF("\nGenerating secret code ...");
            dos.flush();
            dos.writeUTF("\n\nYou have " + numGuesses + " guesses left.\n");
            dos.flush();

            while (numGuesses > 0) {
                if (Game.endGameForAll) {
                    dos.writeUTF("Thank you for playing. Please restart the server to play again.");
                    dos.flush();
                    dis.close();
                    dos.close();
                    break;
                }
                dos.writeUTF("""
                        What is your next guess?
                        Type in the characters for your guess and press enter.
                        Enter guess:\s""");

                dos.flush();

                String input = dis.readUTF();


                UserGuessClass guess = new UserGuessClass(input);
                if (input.equals("HISTORY")) {
                    userHistory.printHistory();
                    dos.writeUTF("\n");
                    dos.flush();
                }

                else if (guess.isValidGuess()) {
                    //compare guess
                    int[] res = guess.compareStrings();
                    int black = res[0];
                    int white = res[1];
                    String result = black + "B_" + white + "W";
                    if (black == GameConfiguration.pegNumber) {
                        //win
                        dos.writeUTF("\n" + input + " -> Result: " + result + " - You win !!\n\n");
                        dos.flush();
                        dos.writeUTF("Thank you for playing. Please restart the server to play again.");
                        dos.flush();
                        endGameForAll = true;
                        break;
                    } else {
                        numGuesses--;
                        if (numGuesses == 0) {
                            dos.writeUTF("\nSorry, you are out of guesses. You lose, boo-hoo. \n\n");
                            dos.flush();

                            break;
                        }
                        dos.writeUTF("\n" + input + " -> Result: " + result);
                        dos.flush();
                        userHistory.addGuess(input, result);
                    }
                    dos.writeUTF("\n\nYou have " + numGuesses + " guesses left.\n");
                    dos.flush();

                } else {
                    //invalid input
                    if (input.equals("")) {
                        dos.writeUTF("\n-> INVALID GUESS\n");
                        dos.flush();
                    } else {
                        dos.writeUTF("\n" + input + " -> INVALID GUESS\n");
                        dos.flush();
                    }
                }


            }

            gameOver = true;

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void newGame(String secretCode, int numGuesses) throws Exception {
        this.numGuesses = numGuesses;
        Game.secretCode = secretCode;
        userHistory.clearHistory();
        gameOver = false;
        play = true;
    }


}

