import java.io.StringReader;
import java.util.*;

public class SpellChecker {

    /**
     * Method for mode 1: When command line arguments are provided, prints a message about every word,
     * indicating whether it is correct or not, and makes suggestions for incorrect words
     * @param inputLine provided command line argument
     */
    public static void modeCommandLine(String inputLine){
        SpellDictionary dict = new SpellDictionary("words.txt");
        // ignore all the punctuations except for " ' "
        String cleanedInputLine = inputLine.replaceAll("(\\p{Punct}", "");
        if (!dict.containsWord(cleanedInputLine)){
            System.out.println("Not found: " + cleanedInputLine);
            System.out.print("Suggestions: ");
            ArrayList<String> returning = dict.nearMisses(cleanedInputLine);
            if (returning.size() == 0){
                System.out.println("none");
            }
            else{
                for(String a: returning){
                    System.out.print(a + " ");
                }
                System.out.println();
            }
        }
        else{
            System.err.println("'" + cleanedInputLine + "' is spelled correctly.");
        }
    }

    /**
     * Method for mode 2: Taking inputs from a file. Read individual words and check their spelling. 
     * If a word is misspelt, print a message and offer suggestions.
     */
    public static void modeReadFile(){
        SpellDictionary dict = new SpellDictionary("words.txt");
        Scanner sc = new Scanner(System.in);
        // making sure that the ``Scanner`` skips over punctuation marks except for " ' "
        ArrayList<String> inputStorage = new ArrayList<>();
        while (sc.hasNextLine()) {
            String temp = sc.next();
            String cleanedTemp = temp.replaceAll("(?!['])\\p{Punct}", "");
            if (!inputStorage.contains(cleanedTemp)){
                inputStorage.add(cleanedTemp);
            }
        }
        Iterator<String> iterator = inputStorage.iterator();
        while(iterator.hasNext()){
            String s = iterator.next();
            if (!dict.containsWord(s)){
                System.out.println("Not found: " + s);
                System.out.print("Suggestions: ");
                ArrayList<String> returning = dict.nearMisses(s);
                if (returning.size() == 0){
                    System.out.println("none");
                }
                else{
                    for(int i = 0; i < returning.size(); i++){
                        if(i != (returning.size() - 1)){
                            System.err.print(returning.get(i) + ", ");
                        }
                        else{
                            System.err.print(returning.get(i));
                        }
                    }
                    System.out.println();
                }
            }
        }
        sc.close();
    }
    public static void main(String[] args) {
        if (args.length == 0) {
            SpellChecker.modeReadFile();
        }
        else {
            String inputLine = "";
            for(int i = 0; i < args.length; i++){
                Scanner scanner = new Scanner(new StringReader(args[i]));
                while (scanner.hasNext()) {
                    inputLine = scanner.next();
                }
                scanner.close();
                SpellChecker.modeCommandLine(inputLine);
            }
        }
    }
}
