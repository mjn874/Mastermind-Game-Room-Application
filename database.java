/* EE422C Assignment #2 submission by
 * Replace <...> with your actual data.
 * Milton Nava
 * mjn874
 */
package assignment5;
import java.io.DataOutputStream;
import java.util.*;

public class database {
    //create array list of guess objects
    //Why did error get corrected by making userDataBase static
    //if I don't define a constructor and I try to add items to userDataBase, will a new object be created?
    private ArrayList<String> userGuessArray;
    private ArrayList<String> userPegs;

    DataOutputStream dos;
    public database(DataOutputStream dos){
        this.dos = dos;
        userGuessArray = new ArrayList<>();
        userPegs = new ArrayList<>();
    }

    public void addGuess(String guess, String result) {
        userGuessArray.add(guess);
        userPegs.add(result);

    }
    public void clearHistory() {
        userGuessArray.clear();
        userPegs.clear();
    }

    public void printHistory(){
        System.out.println();
        for(int i = 0; i < userGuessArray.size(); i++) {
            try{
                dos.writeUTF(userGuessArray.get(i) + "\t\t" + userPegs.get(i));
            }
            catch (Exception e) {System.out.println(e);}

        }
    }


}