import java.util.*;

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
}
