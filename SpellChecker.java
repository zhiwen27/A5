import java.io.StringReader;
import java.util.*;

public class SpellChecker {

    public static void modeCommandLine(String inputLine){
        SpellDictionary dict = new SpellDictionary("words.txt");
        String[] input = inputLine.split(" ");
        for(String s: input){
            if (!dict.containsWord(s)){
                System.out.println("Not found: " + s);
                System.out.print("Suggestions: ");
                ArrayList<String> returning = dict.nearMisses(s);
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
                System.err.println("'" + s + "' is spelled correctly.");
            }
        }
    }

    public static void modeReadFile(){
        SpellDictionary dict = new SpellDictionary("words.txt");
        Scanner sc = new Scanner(System.in);
        // making sure that the ``Scanner`` skips over punctuation marks except for " ' "
        HashSet<String> inputStorage = new HashSet<>();
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
                    for(String a: returning){
                        System.out.print(a + " ");
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
            Scanner scanner = new Scanner(new StringReader(args[0]));
            while (scanner.hasNext()) {
                inputLine += scanner.next() + " ";
            }
            scanner.close();
            SpellChecker.modeCommandLine(inputLine);
        }
    }
}
