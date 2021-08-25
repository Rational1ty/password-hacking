import static java.lang.System.out;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;

public class Password {
    public static void main(String[] args) throws IOException {
        Scanner kb = new Scanner(System.in);

        int[][] difficulty = {
            {4, 5},
            {6, 7, 8},
            {9, 10},
            {11, 12, 13},
            {14, 15}
        };

        // difficulty selection
        out.print("\nDifficulty (1-5): ");
        int diff = kb.nextInt();
        int[] opts = difficulty[diff - 1];

        // generate seperate random values for length of words and number of words
        int rLen = (int) Math.floor(Math.random() * opts.length);
        int rNum = (int) Math.floor(Math.random() * opts.length);

        // pass opts[rNum] as the second arg unless it is 4, in which case pass 5
        String[] possible = getWords(opts[rLen], opts[rNum] == 4 ? 5 : opts[rNum]);
        
        // choose random password
        String pswd = possible[(int) Math.floor(Math.random() * possible.length)];

        out.println();
        for (String s : possible) out.println(s);
        out.println();
        kb.nextLine();

        int guesses = 4;
        while (guesses > 0) {
            out.print("Guess (" + guesses + " left)? ");
            String g = kb.nextLine().toUpperCase();

            if (g.equals(pswd)) break;

            out.println(guess(g, pswd) + "/" + pswd.length() + " correct");
            guesses--;
        }

        if (guesses > 0) out.println("You win!");
        else out.println("You blew up! Correct password: " + pswd);

        kb.close();
    }

    // Returns an array of [amt] words that are all [len] characters long
    static String[] getWords(int len, int amt) throws IOException {
        Scanner dict = new Scanner(new File("D:\\java\\resources\\input\\enable1.txt"));

        ArrayList<String> list = new ArrayList<>();
        String[] words = new String[amt];

        while (dict.hasNextLine()) {
            String word = dict.nextLine();
            if (word.length() == len)
                list.add(word);
        }

        Collections.shuffle(list);
        for (int i = 0; i < amt; i++)
            words[i] = list.get(i).toUpperCase();

        return words;
    }

    static int guess(String g, String ans) {
        int correct = 0;
        for (int i = 0; i < g.length(); i++)
            if (g.charAt(i) == ans.charAt(i))
                correct++;
        return correct;
    }
}