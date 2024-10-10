import java.io.File;
import java.io.FileNotFoundException;
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
    public static void main(String[] args) {
        SpellDictionary dict = new SpellDictionary("words.txt");
        if (args.length == 0) {
            try {
                File file = new File("sonnet.txt");
                Scanner sc = new Scanner(file);
                // making sure that the ``Scanner`` skips over punctuation marks.
                Hashtable<String,Integer> inputStorage = new Hashtable<>();
                while (sc.hasNextLine()) {
                    String temp = sc.next();
                    String cleanedTemp = temp.replaceAll("(?!['])\\p{Punct}", "");
                    if (inputStorage.containsKey(cleanedTemp)){
                        inputStorage.put(cleanedTemp, inputStorage.get(cleanedTemp) + 1);
                    }
                    else{
                        inputStorage.put(cleanedTemp,1);
                    }
                }
                Enumeration<String> enumeration = inputStorage.keys();
                while(enumeration.hasMoreElements()){
                    String s = enumeration.nextElement();
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
            } catch (FileNotFoundException e) {
                System.out.println("Cannot locate file.");
                System.exit(-1);
            }
        }
        else {
            // Otherwise, echo what was read in for now
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
