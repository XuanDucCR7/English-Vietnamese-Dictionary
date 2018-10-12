package Dictionary;

import java.sql.*;

public class DatabaseConnection {

    public static Connection getConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:C:/Users/Khoa/Desktop/km-dictionary/GUI-1.0/Database/dict_hh.db");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
