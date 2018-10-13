package Controller;

import Dictionary.LanguageCode;
import Dictionary.TextToSpeak;
import Dictionary.showAlert;
import com.darkprograms.speech.translator.GoogleTranslate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class controllerGoogleTranslate extends GenaralController implements Initializable {

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
            "English", "Vietnamese", "Japan", "Thailand"
    );
    ObservableList<String> OptionTarget = FXCollections.observableArrayList(
            "English", "Vietnamese", "Japan", "Thailand"
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

    public void chooseSourceSpeech(ActionEvent e){
        TextToSpeak.playSound(sourceText.getText());
    }

    public void chooseTargetSpeech(ActionEvent e){
        TextToSpeak.playSound(targetText.getText());
    }

    //
    public void getSourceText(KeyEvent e) throws IOException {
        if(SourceLanguageCode == null || TargetLanguageCode == null){
            showAlert.AlertInfo("Please choose language");
            sourceText.setText("");
            targetText.setText("");
        }else{
            try{
                targetText.setText(GoogleTranslate.translate(SourceLanguageCode,TargetLanguageCode,sourceText.getText()));
            }catch(IOException ex){
                ex.getStackTrace();
            }
        }


    }

    public void goBackDashBoard(ActionEvent e) throws IOException {
        changeScene(e,"../fxml/DashBoard.fxml");
    }

}
