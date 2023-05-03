/* EE422C Assignment #2 submission by
 * Replace <...> with your actual data.
 * Milton Nava
 * mjn874
 */
package assignment5;

public class UserGuessClass{
//instance variables i.e. pegs, guess,
private String userGuess;


    public UserGuessClass(String userGuess) {
        this.userGuess = userGuess;
    }

    public boolean isValidGuess() {
        if(userGuess.length() != GameConfiguration.pegNumber) {
            return false;
        }
        for(int i = 0; i < userGuess.length(); i++) {
            if(!Game.colors.contains(Character.toString(userGuess.charAt(i)))) {
                return false;
            }
        }

        return true;
    }

    public int[] compareStrings() {
        int bPegs = 0;
        int wPegs = 0;

        char[] guessChars = userGuess.toCharArray();
        char[] codeChars = Game.secretCode.toCharArray();

        for(int i = 0; i < GameConfiguration.pegNumber; i++) {
            if(guessChars[i] == codeChars[i]) {
                guessChars[i] = '-';
                codeChars[i] = '-';
                bPegs += 1;
            }
        }
        for(int i = 0; i < GameConfiguration.pegNumber; i++) {
            if(guessChars[i] == '-') {
                continue;
            }
            for(int j = 0; j < GameConfiguration.pegNumber; j++) {
                if(codeChars[j] == '-') {
                    continue;
                }
                if(codeChars[j] == guessChars[i]) {
                    codeChars[j] = '-';
                    guessChars[i] = '-';
                    wPegs += 1;
                    break;
                }
            }
        }

        return new int[]{bPegs,wPegs};
    }

}

