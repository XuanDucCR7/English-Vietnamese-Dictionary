import java.util.*;

public class DictionaryManagement {

    public void insertFromCommandline(Dictionary dictionary){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter \\Stop: if you want to stop insert word.");


        //nhap word va explain tu ban phim

        int index = 1;
        boolean insert = true;
        String check;
        while(insert){
            Word word =  new Word();

            //insert word
            System.out.println("Word " + index + ": ");
            check = scan.nextLine();

            if(check.equals("\\Stop") || check.equals("\\stop")){
                insert = false;
                break;
            }
            else
                word.setWord_target(check);

            //insert explain
            System.out.println("Explain: ");
            check = scan.nextLine();

            if(check.equals("\\Stop") || check.equals("\\stop")){
                insert = false;
                break;
            }
            else
                word.setWord_explain(check);

            //truong hop nhap word khong nhap explain or nguoc lai
            if(word.getWord_target() != null && word.getWord_explain() != null){
                dictionary.setDictionary(word);
            }

            index ++;
        }


    }
}
