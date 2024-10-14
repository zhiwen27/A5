import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
        while (file.hasNextLine()) {
            storage.add(file.next().toLowerCase()); // convert all the words to the lower case
        }
        file.close();
    }
    public boolean containsWord(String query){
        query = query.toLowerCase(); // convert the input to lower case
        query = query.replaceAll("(?!['])\\p{Punct}", ""); // ignore all the punctuations except for " ' "
        for (String s: this.storage){
            if (s.equals(query)){
                return true;
            }
        }
        return false;
    }
    public ArrayList<String> nearMisses(String query){

        // Create a hashset to store all the possible alternatives and store non-repeated words
        HashSet<String> alternatives = new HashSet<>();
        query = query.toLowerCase(); // convert the input to lower case
        query = query.replaceAll("(?!['])\\p{Punct}", ""); // ignore all the punctuations except for " ' "
        // Deletions: Delete one letter from the word.
        for(int i = 0; i < query.length(); i++){
            String temp = query.substring(0, i) + query.substring(i+1, query.length());
            if(this.storage.contains(temp)){
                alternatives.add(temp);
            }
        }

        // Insertions: Insert one letter of lower case into the word at any point, including " ' ".
        for(int i = 0; i < query.length() + 1; i++){
            for(int j = 97; j < 123; j++){
                String temp = query.substring(0, i) + (char)j + query.substring(i, query.length());
                if(this.storage.contains(temp)){
                    alternatives.add(temp);
                }
            }
            String temp = query.substring(0, i) + "'" + query.substring(i, query.length());
            if(this.storage.contains(temp)){
                alternatives.add(temp);
            }
        }

        // Substitutions: Replace one character with another in lower case. 
        for(int i = 0; i < query.length(); i++){
            for(int j = 97; j < 123; j++){
                if ((char)j != query.charAt(i)){ // would replace with another character
                    String temp = query.substring(0, i) + (char)j + query.substring(i + 1, query.length());
                    if(this.storage.contains(temp)){
                        alternatives.add(temp);
                    }
                }
            }
            String temp = query.substring(0, i) + "'" + query.substring(i + 1, query.length());
            if(this.storage.contains(temp)){
                alternatives.add(temp);
            }
        }

        // Transpositions: Swap two adjacent characters.
        for(int i = 0; i < query.length() - 1; i++){
            Character tempElement = query.charAt(i);
            String temp = query.substring(0, i) + query.charAt(i + 1) + query.substring(i + 1, query.length());
            String swapped = temp.substring(0, i + 1) + tempElement + temp.substring(i + 2, query.length());
            if(this.storage.contains(swapped)){
                alternatives.add(swapped);
            }
        }

        // Splits: Divide the word into two legal words. For this kind of near miss, the pair of words together should be recorded
        // as a single entry, with a space between them. 
        for(int i = 1; i < query.length(); i++){
            String before = query.substring(0, i);
            String after = query.substring(i, query.length());
            if ((this.storage.contains(before)) && (this.storage.contains(after))){
                alternatives.add(before + " and " + after);
            }
        }

        // Splits: Divide the word into two legal words. For this kind of near miss, the pair of words together should be recorded
        // as a single entry, with a " ' " between them. 
        for(int i = 1; i < query.length(); i++){
            String temp = query.substring(0, i) + "'" + query.substring(i, query.length());
            if(this.storage.contains(temp)){
                alternatives.add(temp);
            }
        }

        // return the ones that are contained in the dictionary with an arraylist
        ArrayList<String> returning = new ArrayList<>();
        Iterator<String> iterator = alternatives.iterator();
        while(iterator.hasNext()){
            String temp = iterator.next();
            returning.add(temp);
        }
        return returning;
    }

    public static void main(String[] args) {
        SpellDictionary a = new SpellDictionary("words.txt");
        ArrayList<String> returning = new ArrayList<>();
        // Test for containsWord()
        System.err.print("Test for containsWord(), see if it ignores punctuation and capitalization:" + "\n" + "Input: Flew." + "\n" + "Output: ");
        System.err.println(a.containsWord("Flew."));
        // Tests for different styles in nearMisses()
        returning = a.nearMisses("qeust");
        System.err.print("Test for swapping characters: qeust -> quest" + "\n" + "Input: qeust" + "\n" + "Suggestions: ");
        for(String s:returning){
            System.err.println(s);
        }
        returning = a.nearMisses("qiet");
        System.err.print("Test for adding and substituting characters: qiet -> quiet diet" + "\n" + "Input: qiet" + "\n" + "Suggestions: ");
        for(String s:returning){
            System.err.print(s + " ");
        }
        System.err.println();
        returning = a.nearMisses("qeust.");
        System.err.print("Test for punctuation: qeust. -> quest" + "\n" + "Input: qeust." + "\n" + "Suggestions: ");
        for(String s:returning){
            System.err.println(s);
        }
        returning = a.nearMisses("abatements");
        System.err.print("Test for splitting characters (adding ' in between, a supplement of splitting with spaces): abatements -> abatement abatement and s abatement's)" + "\n" + "Input: abatements" + "\n" + "Suggestions: ");
        for(String s:returning){
            System.err.print(s + " ");
        }
    }
}
