import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        Dictionary dictionary = new Dictionary();
        DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();

        dictionaryCommandline.dictionaryAdvanced(dictionary);
    }
}
