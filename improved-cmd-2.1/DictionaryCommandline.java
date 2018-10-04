import java.util.*;
import java.io.*;

public class DictionaryCommandline {
    
    public void showAllWord(Dictionary dictionary){
        System.out.println("No\t|English\t\t|Vietnamese");
        int index = 1;
        for(Word dic : dictionary.getDictionary()){
            System.out.println(index + "\t|" + dic.getWord_target() + formatSpace(dic.getWord_target())+ "|"  + dic.getWord_explain());
            index++;
        }
    }


    // dinh dang khoang cach khi in ra
    public String formatSpace(String st){
        int length = 18 - st.length();
        String space = " ";
        for(int i = 0; i < length; i++)
            space += " ";
        return space;
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

    public void dictionaryAdvanced(Dictionary dictionary) throws IOException{

        System.out.println("Please enter:" +
                "\n1: If you want to insert a list of words from file." +
                "\n2: If you want to insert word." +
                "\n3: If you want to show all words." +
                "\n4: If you want to search words." +
                "\n5: If you want to delete a word from dictionary." +
                "\n6: If you to change a word." +
                "\n7: If you want to export to file" +
                "\n8: Exit.");

        DictionaryManagement management = new DictionaryManagement();
        Scanner scan = new Scanner(System.in);
        String check = scan.nextLine();
        int request = 0;

        //neu nguoi dung nhap vao kieu khaa integer
        try{
            request = Integer.parseInt(check);
        }catch(NumberFormatException e){
            dictionaryAdvanced(dictionary);
        }

        switch(request){
            case 1:
                management.insertFromFile(dictionary);
                dictionaryAdvanced(dictionary);    //ínsert xong quay lai menu chinh
                break;
            case 2:
                management.insertFromCommandline(dictionary);
                dictionaryAdvanced(dictionary);    //quay lai menu chinh
                break;
            case 3:
                showAllWord(dictionary);
                dictionaryAdvanced(dictionary);    //quay lai menu chinh
                break;
            case 4:
                //management.dictionaryLookup(dictionary);
                dictionarySearcher(dictionary);
                dictionaryAdvanced(dictionary);
                break;
            case 5:
                management.dictionaryDelete(dictionary);
                dictionaryAdvanced(dictionary);
                break;
            case 6:
                management.dictionaryChange(dictionary);
                dictionaryAdvanced(dictionary);
                break;
            case 7:
                management.dictionaryExportToFile(dictionary);
                dictionaryAdvanced(dictionary);
                break;
            case 8:
                System.exit(0);                 //thoat chuong trinh
                break;
            default:
                dictionaryAdvanced(dictionary);
                break;
        }
    }

    public void dictionarySearcher(Dictionary dictionary){
        Scanner scan = new Scanner(System.in);

        System.out.print("Please enter your word: ");
        String searcher = scan.nextLine();

        System.out.println("No\t|English\t\t\t|Vietnamese");
        int index = 1;
        for(Word word : dictionary.getDictionary()){
            if(word.getWord_target().indexOf(searcher) == 0){
                System.out.println(index + "\t|" + word.getWord_target() + formatSpace(word.getWord_target())+ "|"  + word.getWord_explain());
                index++;
            }
        }
    }
}
