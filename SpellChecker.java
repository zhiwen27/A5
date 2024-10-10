import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellChecker {

    public static void modeCommandLine(String inputLine){
        SpellDictionary dict = new SpellDictionary("words.txt");
        String[] input = inputLine.split(" ");
            for(String s: input){
                if (!dict.containsWord(s)){
                    System.err.println("Not found: " + s);
                    System.err.print("Suggestions: ");
                    ArrayList<String> alternatives = dict.nearMisses(s);
                    for(String a: alternatives){
                        System.err.print(a + " ");
                    }
                    System.err.println();
                }
                else{
                    System.err.println("'" + s + "' is spelled correctly.");
                }
            }
    }
    public static void main(String[] args) {
        SpellDictionary dict = new SpellDictionary("words.txt");
        String inputLine = "";
        if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);
            String filename = scanner.nextLine();
            try {
                scanner = new Scanner(new File(filename));
                // making sure that the ``Scanner`` skips over punctuation marks.
            } catch (FileNotFoundException e) {
                System.err.println("Cannot locate file.");
                System.exit(-1);
            }
            while (scanner.hasNextLine()) {
                inputLine += scanner.next() + " ";
                String[] input = inputLine.split(" ");
                for(String s: input){
                    if (!dict.containsWord(s)){
                        System.err.println("Not found: " + s);
                        System.err.print("Suggestions: ");
                        ArrayList<String> alternatives = dict.nearMisses(s);
                        for(String a: alternatives){
                            System.err.print(a + " ");
                        }
                        System.err.println();
                    }
                    else{
                        System.err.println("'" + s + "' is spelled correctly.");
                    }
                }
            }
            scanner.close();
        } else {
            // Otherwise, echo what was read in for now
            Scanner scanner = new Scanner(new StringReader(args[0]));
            while (scanner.hasNext()) {
                inputLine += scanner.next() + " ";
            }
            scanner.close();
            SpellChecker.modeCommandLine(inputLine);
        }
    }
}
