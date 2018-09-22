import java.util.*;

public class Dictionary {
    private ArrayList<Word> dictionary = new ArrayList<Word>();

    public void setDictionary(Word word) {

        this.dictionary.add(word);
    }

    public ArrayList<Word> getDictionary() {
        return dictionary;
    }

}
