package Controller;

import Dictionary.showAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;

import java.io.IOException;


public class controllerAddWord extends GenaralController {
    @FXML
    private TextArea Word;
    @FXML
    private TextArea Explain;


    public void add(ActionEvent e){
        FXMLLoader loader;
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Dashboard.fxml"));
        try
        {
            loader.load();
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }

        controllerDashboard controller =loader.getController();
        controller.addWord(Word.getText(), Explain.getText());
        Word.setText("");
        Explain.setText("");
        showAlert.AlertInfo("Successful!");
    }

    public void goBackDashBoard(ActionEvent e) throws IOException {
        changeScene(e,"../fxml/DashBoard.fxml");
    }
}
