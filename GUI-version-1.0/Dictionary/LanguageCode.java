package Dictionary;
import java.lang.String;

public enum LanguageCode {
    Vietnamese("vi"),
    English("en"),
    Japan("ja"),
    German("db");


    private String code;

    LanguageCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}