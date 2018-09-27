import java.util.*;

public class DictionaryCommandline {
    
    public void showAllWord(Dictionary dictionary){
        System.out.println("No\t|English\t\t\t|Vietnamese");
        int index = 1;
        for(Word dic : dictionary.getDictionary()){
            System.out.println(index + "\t|" + dic.getWord_target() + formatSpace(dic.getWord_target())+ "|" + dic.getWord_explain());
            index++;
        }
    }



    public String formatSpace(String st){
        int length = 18 - st.length();
        String space = " ";
         
        for(int i = 0; i < length; i++)
            space += " ";
        return space;
    }

    public void dictionaryBasic(Dictionary dictionary){
        System.out.println("Please enter:\n1: If you want to insert word.\n2: If you want to show all words." +
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

}
