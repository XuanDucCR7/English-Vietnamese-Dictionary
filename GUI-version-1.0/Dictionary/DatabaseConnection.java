package Dictionary;

import java.sql.*;

public class DatabaseConnection {

    public static Connection getConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:D:/PROJECT/KM-DIctionary-pro/src/Database/dict_hh.db");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Connection getConnection1() {

        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:D:/PROJECT/KM-DIctionary-pro/src/Database/dict_hh.db");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
