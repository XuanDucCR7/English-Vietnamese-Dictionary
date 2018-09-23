import java.util.*;
import java.io.*;

public class DictionaryManagement {

    public void insertFromCommandline(Dictionary dictionary){
        Scanner scan = new Scanner(System.in);
        System.out.print("Please enter the number of words you want to insert: ");

        int numWords = scan.nextInt();
        scan.nextLine(); //loai dau xuong dong thua

        //nhap word va explain tu ban phim
        String check;
        for (int i = 0; i < numWords; ++i) {
            Word word =  new Word();

            //insert word
            System.out.print("Word " + (i + 1) + ": ");
            check = scan.nextLine();
            word.setWord_target(check);

            //insert explain
            System.out.print("Explain: ");
            check = scan.nextLine();
            word.setWord_explain(check);

            //truong hop nhap word khong nhap explain or nguoc lai
            if(word.getWord_target() != null && word.getWord_explain() != null){
                dictionary.setDictionary(word);
            }
        }
    }

    public void insertFromFile(Dictionary dictionary) throws IOException {

        InputStream in = new FileInputStream("dictionaries.txt");
        BufferedReader buf = new BufferedReader(new InputStreamReader(in));

        String line = "";
        while (line != null) {

            line = buf.readLine();
            if (line == null) { // check EOF
                break;
            }

            int index = 0;
            String target = "", explain;
            Word word = new Word();

            // get word
            for (index = 0; line.charAt(index) != '\t' && line.charAt(index) != ' '; ++index) {
                target += line.charAt(index);
            }
            // skip whitespace
            for (; line.charAt(index) == '\t' || line.charAt(index) == ' '; ++index);
            // get explain
            explain = line.substring(index);

            word.setWord_target(target);
            word.setWord_explain(explain);

            //truong hop nhap word khong nhap explain or nguoc lai
            if(word.getWord_target() != null && word.getWord_explain() != null){
                dictionary.setDictionary(word);
            }
        }
    }

    public void dictionaryLookup(Dictionary dictionary) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Please enter your word: ");
        String lookup = scan.nextLine();
        System.out.println(lookup);

        ArrayList<Word> wordList = dictionary.getDictionary(); // danh sach cac tu trong database

        for (Word word: wordList) { // vong lap tim tu can tra
            if (lookup.equals(word.getWord_target())) {
                System.out.println("=> " + word.getWord_target() + ": " + word.getWord_explain());
                return;
            }
        }
        // het vong lap -> ko tim thay tu can tra
        System.out.println("Sorry! We can't find the word you're looking for!");
    }
}
