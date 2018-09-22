public class Main {
    public static void main(String []args){
        Dictionary dictionary = new Dictionary();
        DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();

        boolean play = true;
        while(play){
            dictionaryCommandline.dictionaryBasic(dictionary);
            play = dictionaryCommandline.getPlay();
        }

    }
}
