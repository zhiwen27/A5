import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellChecker {
    public static void main(String[] args) {
        String inputLine = "";
        if (args.length == 0) {
            // If no arguments passed, print instructions
            System.err.println("Usage:  java Postfix <expr>");
        } else {
            // Otherwise, echo what was read in for now
            Scanner scannerTest = new Scanner(new StringReader(args[0]));
            while (scannerTest.hasNext()) {
                inputLine += scannerTest.next() + " ";
            }
            scannerTest.close();
        }
        String[] input = inputLine.split(" ");
        SpellDictionary dict = new SpellDictionary("words.txt");
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
}
