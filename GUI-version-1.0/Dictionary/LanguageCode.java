package Dictionary;

import java.util.HashMap;

public class LanguageCode{
    private HashMap<String, String> LanguageCode = new HashMap<String, String>();
    public LanguageCode(){
        LanguageCode.put("Vietnamese","vi");
        LanguageCode.put("English","en");
        LanguageCode.put("Japan","ja");
        LanguageCode.put("German","db");
    }

    public String getLanguageCode(String Language){
        return LanguageCode.get(Language);
    }
}