package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.web.HTMLEditor;

import java.io.IOException;


public class controllerAddWord extends GeneralController {
    @FXML
    private TextArea Word;
    @FXML
    private HTMLEditor Explain;


    public void add(ActionEvent e){
        addWord(Word.getText(), Explain.getHtmlText());
        Word.setText("");
        Explain.setHtmlText("");
    }

    public void goBackDashBoard(ActionEvent e) throws IOException {
        changeScene(e,"../fxml/DashBoard.fxml");
    }
}
