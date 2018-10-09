package Controller;

import Dictionary.GoogleTranslate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.Vector;

public class controllerGoogleTranslate implements Initializable {

    @FXML
    private TextArea sourceText;
    @FXML
    private TextArea targetText;

    private MenuItem Vietnamese = new MenuItem("Vietnamese");
    private MenuItem English = new MenuItem("English");

    @FXML
    private MenuButton sourceLanguage = new MenuButton("Language",null,Vietnamese,English);

    @FXML
    private MenuButton targetLanguage = new MenuButton("Language",null,Vietnamese,English);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //Menu button
    public void clickSourceVietnamese (ActionEvent e){
        sourceLanguage.setText(Vietnamese.getText());
    }

    public void clickTargetVietnamese (ActionEvent e){
        targetLanguage.setText(Vietnamese.getText());
    }

    public void clickSourceEnglish (ActionEvent e){
        sourceLanguage.setText(English.getText());
    }

    public void clickTargetEnglish (ActionEvent e){
        targetLanguage.setText(English.getText());
    }

    //SourceText and TargetText
    Stack<String> stackSourceText = new Stack();
    Vector<String> vectorSource = new Vector();
    String temptSourceText = "";
    public void getSourceText(KeyEvent e) throws IOException {
        temptSourceText = sourceText.getText();

        switch (e.getCode()){
            case BACK_SPACE: {
                if (stackSourceText.size() > 1) {
                    stackSourceText.pop();
                    temptSourceText = sourceText.getText();
                    System.out.println(stackSourceText.peek());
                }
                else{
                    stackSourceText.remove(0);
                    temptSourceText = sourceText.getText();
                    targetText.setText("");
                    System.out.println(stackSourceText.peek());
                }
                break;
            }
            default:
                stackSourceText.push(temptSourceText);
                break;
        }


        try{
            //System.out.println(GoogleTranslate.getDisplayLanguage("VietnamesE"));
            targetText.setText(GoogleTranslate.translate("en","vi",stackSourceText.peek()));
        }catch(IOException ex){
            ex.getStackTrace();
        }

    }

    public void goBackDashBoard(ActionEvent e) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        Parent DashBoardView = FXMLLoader.load(getClass().getResource("../fxml/DashBoard.fxml"));
        Scene scene = new Scene(DashBoardView);
        stage.setScene(scene);
        stage.show();
    }

}
