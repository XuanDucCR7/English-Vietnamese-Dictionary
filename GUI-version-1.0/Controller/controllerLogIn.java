package Controller;

import Dictionary.showAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class controllerLogIn extends GeneralController{
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    String us = null;
    String ps = null;

    public void logIn(ActionEvent e1){
        us = username.getText();
        ps = password.getText();
        String query = "SELECT username FROM users WHERE username = ?";
        try{
            st = con.prepareStatement(query);
            st.setString(1,us);
            //st.setString(2,ps);
            rs = st.executeQuery();
            if(rs.next()){
                changeScene(e1,"../fxml/Dashboard.fxml");

            }
            else{
                showAlert.AlertInfo("Username or password is incorrect!!");
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
