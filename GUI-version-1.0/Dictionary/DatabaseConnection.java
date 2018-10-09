package Dictionary;

import java.sql.*;

public class DatabaseConnection {

    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String password = ""; // nhap lai mat khau mysql o day
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/Eng-Viet_Dictionary?autoReconnect=true&useSSL=false", "root", password);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
