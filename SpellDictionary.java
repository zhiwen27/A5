import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class SpellDictionary implements SpellingOperations{

    public HashSet<String> storage = new HashSet<>();
    public SpellDictionary(String input){
        Scanner file = null;
        try {
            file = new Scanner(new File(input));
        } catch (FileNotFoundException e) {
            System.err.println("Cannot locate file.");
            System.exit(-1);
        }
        // ❗️ what is " 's " in the dic?
        while (file.hasNextLine()) {
            storage.add(file.next());
        }
        file.close();
    }
    public boolean containsWord(String query){
        // convert the string to lower case
        query = query.toLowerCase();
        for (String s: this.storage){
            if (s.equals(query)){
                return true;
            }
        }
        return false;
    }
    public ArrayList<String> nearMisses(String query){
        ArrayList<String> alternatives = new ArrayList<>();
        // convert the string to lower case
        query = query.toLowerCase();
        // Deletions: Delete one letter from the word.
        for(int i = 0; i < query.length(); i++){
            alternatives.add(query.substring(0, i) + query.substring(i+1, query.length()));
        }

        // Insertions: Insert one letter into the word at any point.
        for(int i = 0; i < query.length() + 1; i++){
            for(int j = 97; j < 123; j++){
                String temp = query.substring(0, i) + (char)j + query.substring(i, query.length());
                alternatives.add(temp);
            }
        }

        // Substitutions: Replace one character with another. 
        for(int i = 0; i < query.length(); i++){
            for(int j = 97; j < 123; j++){
                if ((char)j != query.charAt(i)){ // would replace with another character
                    alternatives.add(query.substring(0, i) + (char)j + query.substring(i + 1, query.length()));
                }
            }
        }

        // Transpositions: Swap two adjacent characters.
        for(int i = 0; i < query.length() - 1; i++){
            String temp = "";
            Character tempElement = query.charAt(i);
            temp = query.substring(0, i) + query.charAt(i + 1) + query.substring(i + 1, query.length());
            alternatives.add(temp.substring(0, i + 1) + tempElement + temp.substring(i + 2, query.length()));
        }

        // Splits: Divide the word into two legal words. For this kind of near miss, the pair of words together should be recorded
        // as a single entry, with a space between them. 
        for(int i = 1; i < query.length(); i++){
            alternatives.add(query.substring(0, i) + " " + query.substring(i, query.length()));
        }

        // return the ones that are contained in the dictionary
        ArrayList<String> returning = new ArrayList<>();
        for(int i = 0; i < alternatives.size(); i++){
            if(this.storage.contains(alternatives.get(i))){
                returning.add(alternatives.get(i));
            }
        }
        return returning;
    }

    public static void main(String[] args) {
        SpellDictionary a = new SpellDictionary("words.txt");
        a.nearMisses("qest");
    }
}
