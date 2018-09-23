import java.util.*;
import java.io.*;

public class DictionaryCommandline {
    
    public void showAllWord(Dictionary dictionary){
        System.out.println("No\t|English\t|Vietnamese");
        int index = 1;
        for(Word dic : dictionary.getDictionary()){
            System.out.println(index + "\t|" + dic.getWord_target() + "\t|" + dic.getWord_explain());
            index++;
        }
    }

    public void dictionaryBasic(Dictionary dictionary){
        System.out.println("Please enter:\n1: If you want to insert a word.\n2: If you want to show all words." +
                "\n3: Exit.");

        DictionaryManagement management = new DictionaryManagement();
        Scanner scan = new Scanner(System.in);
        int request = scan.nextInt();

        switch(request){
            case 1:
                management.insertFromCommandline(dictionary);
                dictionaryBasic(dictionary);    //ínsert xong quay lai menu chinh
                break;
            case 2:
                showAllWord(dictionary);
                dictionaryBasic(dictionary);    //quay lai menu chinh
                break;
            case 3:
                System.exit(0);                 //thoat chuong trinh
                break;
            default:
                dictionaryBasic(dictionary);
                break;
        }
    }

    public void dictionaryAdvanced(Dictionary dictionary) throws IOException {

        System.out.println("Please enter:\n1: If you want to insert a list of words from file.\n2: If you want to show all words." +
                "\n3: If you want to look up a word.\n4: Exit.");

        DictionaryManagement management = new DictionaryManagement();
        Scanner scan = new Scanner(System.in);
        int request = scan.nextInt();

        switch(request){
            case 1:
                management.insertFromFile(dictionary);
                dictionaryAdvanced(dictionary);    //ínsert xong quay lai menu chinh
                break;
            case 2:
                showAllWord(dictionary);
                dictionaryAdvanced(dictionary);    //quay lai menu chinh
                break;
            case 3:
                management.dictionaryLookup(dictionary);
                dictionaryAdvanced(dictionary);
            case 4:
                System.exit(0);                 //thoat chuong trinh
                break;
            default:
                dictionaryAdvanced(dictionary);
                break;
        }
    }
}
