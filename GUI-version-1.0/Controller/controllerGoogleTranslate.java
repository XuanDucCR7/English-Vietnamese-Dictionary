package Controller;

import Dictionary.GoogleTranslate;
import Dictionary.LanguageCode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    //Combo Box
    @FXML
    private ComboBox<String> SourceLanguage;
    @FXML
    private ComboBox<String> TargetLanguage;

    //LangueCode
    private String SourceLanguageCode;
    private String TargetLanguageCode;
    private LanguageCode Code = new LanguageCode();

    ObservableList<String> OptionSource = FXCollections.observableArrayList(
            "English", "Vietnamese", "Japan", "German"
    );
    ObservableList<String> OptionTarget = FXCollections.observableArrayList(
            "English", "Vietnamese", "Japan", "German"
    );

    public controllerGoogleTranslate() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SourceLanguage.setItems(OptionSource);
        TargetLanguage.setItems(OptionTarget);
    }

    public void chooseSourceLanguage(ActionEvent e) throws IOException {
        SourceLanguageCode = Code.getLanguageCode(SourceLanguage.getValue());
        sourceText.setText(GoogleTranslate.translate(SourceLanguageCode,sourceText.getText()));
    }

    public void chooseTargetLanguage(ActionEvent e) throws IOException {
        TargetLanguageCode = Code.getLanguageCode(TargetLanguage.getValue());
        targetText.setText(GoogleTranslate.translate(TargetLanguageCode,targetText.getText()));
    }

    //SourceText and TargetText
    public void getSourceText(KeyEvent e) throws IOException {
        try{
            targetText.setText(GoogleTranslate.translate(SourceLanguageCode,TargetLanguageCode,sourceText.getText()));
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
