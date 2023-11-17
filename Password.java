import static java.lang.System.out;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Password {
    public static void main(String[] args) throws IOException {
        Scanner kb = new Scanner(System.in);
        Random rd = new Random();

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
        int rlen = rd.nextInt(opts.length);
        int rnum = rd.nextInt(opts.length);

        // pass opts[rNum] as the second arg unless it is 4, in which case pass 5
        var possible = getWords(opts[rlen], opts[rnum] == 4 ? 5 : opts[rnum]);
        
        // choose random password
        int randIndex = rd.nextInt(possible.size());
        String pswd = possible.get(randIndex);

        out.println();

        possible.forEach(out::println);
        
        out.println();
        kb.nextLine();

        int guesses = 4;
        while (guesses > 0) {
            out.printf("Guess (%d left)? ", guesses);
            String g = kb.nextLine().toUpperCase();

            if (g.equals(pswd)) break;

            out.printf("%d/%d correct%n", guess(g, pswd), pswd.length());
            guesses--;
        }

        out.println(guesses > 0 ? "You win!" : ("You blew up! Correct password: " + pswd));

        kb.close();
    }

    // Returns an list of [amt] words that are all [len] characters long
    static List<String> getWords(int len, int amt) throws IOException {
        var words = Files.readAllLines(Paths.get("C:\\users\\mdavi\\prog\\enable1.txt"));

        words.removeIf(word -> word.length() != len);

        Collections.shuffle(words);

        return words.stream()
            .map(String::toUpperCase)
            .toList()
            .subList(0, amt);
    }

    static int guess(String g, String ans) {
        int correct = 0;
        for (int i = 0; i < g.length(); i++)
            if (g.charAt(i) == ans.charAt(i))
                correct++;
        return correct;
    }
}