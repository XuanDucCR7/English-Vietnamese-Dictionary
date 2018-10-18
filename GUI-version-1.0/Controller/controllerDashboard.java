package Controller;

import Dictionary.SpeechToText;
import Dictionary.TextToSpeak;
import Dictionary.showAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class controllerDashboard extends GeneralController implements Initializable {
    @FXML
    private TextField search;
    @FXML
    private ListView<String> listWord;
    @FXML
    private ListView<String> listWordFavorite;
    @FXML
    private ListView<String> listWordRecent;
    @FXML
    private WebView webView;
    @FXML
    private TabPane tabPane;
    @FXML
    private MenuBar menuBar;
    private WebEngine webEngine;

    private void loadDatabase() {
        String query = "SELECT word FROM av";
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                items.add(rs.getString(1));
            }
            listWord.setItems(items);
            st.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        String query1 = "SELECT word FROM av WHERE favorite = 1";
        try{
            st = con.prepareStatement(query1);
            rs = st.executeQuery();

            favoriteWord.clear();
            while (rs.next()) {
                favoriteWord.add(rs.getString(1));
                listWordFavorite.setItems(favoriteWord);
            }
            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String query2 = "SELECT word FROM av WHERE recent > 0 ORDER BY recent DESC";
        try{
            st = con.prepareStatement(query2);
            rs = st.executeQuery();

            recentWord.clear();
            while (rs.next()) {
                recentWord.add(rs.getString(1));
                listWordRecent.setItems(recentWord);
            }
            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDeleteEvent() {
        String currentTab = tabPane.getSelectionModel().getSelectedItem().getText();
        if (currentTab.equals("Search")) {
            deleteWord(listWord, webEngine);
            refreshListWordFavorite();
            refreshListWordRecent();
        } else if (currentTab.equals("Recent")) {
            deleteWord(listWordRecent, webEngine);
            refreshListWordFavorite();
            refreshListWordRecent();
        } else if (currentTab.equals("Favourite")) {
            deleteWord(listWordFavorite, webEngine);
            refreshListWordFavorite();
            refreshListWordRecent();
        }

    }

    private void displaySelectedItem(ListView<String> list) {
        String query = "SELECT html FROM av WHERE word=?";
        String query1 = "SELECT MAX(recent) FROM av";
        String query2 = "UPDATE av SET recent=? WHERE word=?";

        try {
            st = con.prepareStatement(query);
            st.setString(1, list.getSelectionModel().getSelectedItem());
            rs = st.executeQuery();

            while (rs.next()) {
                webEngine.loadContent(rs.getString(1));
            }

            st = con.prepareStatement(query1);
            rs = st.executeQuery();

            if (list != listWordRecent) {

                int maxValue = 0;
                if (rs.getObject(1) != null) maxValue = rs.getInt(1);

                st = con.prepareStatement(query2);
                st.setString(1, String.valueOf(maxValue + 1));
                st.setString(2, list.getSelectionModel().getSelectedItem());
                st.executeUpdate();

                st.close();
                rs.close();
                refreshListWordRecent();
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private void handleSelectEvent(ListView<String> list) {
        list.setOnMouseClicked(e -> {
            displaySelectedItem(list);
        });

        list.setOnKeyPressed(e -> {
            KeyCode keyCode = e.getCode();
            if (keyCode == KeyCode.ENTER) {
                displaySelectedItem(list);
            }
        });
    }

    @FXML
    public void handleSearchEvent() {
        listWord.setItems(filteredItems);

        search.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredItems.setPredicate(word -> {
                if (newValue == null || newValue.isEmpty()) return true;

                String lowerCaseFilter = newValue.toLowerCase();
                if (word.toLowerCase().startsWith(lowerCaseFilter)) return true;

                return false;
            });
            listWord.setItems(filteredItems);
        });
    }

    //Speak
    @FXML
    public void TextToSpeak(){
        String currentTab = tabPane.getSelectionModel().getSelectedItem().getText();
        String text = null;

        if (currentTab.equals("Search")) {
            text = listWord.getSelectionModel().getSelectedItem();
        } else if (currentTab.equals("Recent")) {
            text = listWordRecent.getSelectionModel().getSelectedItem();
        } else if (currentTab.equals("Favourite")) {
            text = listWordFavorite.getSelectionModel().getSelectedItem();
        }
        TextToSpeak.playSound(text);
    }

    private int status = -1;
    @FXML
    public void SpeechToText(){
        status = status * -1;
        if(status == 1){
            SpeechToText.startSpeechRecognition(search, "en");
            handleSearchEvent();

        }
        else if(status == -1){
            SpeechToText.stopSpeechRecognition();
        }
    }
    @FXML
    public void addFavoriteWord() {
        String currentTab = tabPane.getSelectionModel().getSelectedItem().getText();
        String selected = null;

        if (currentTab.equals("Search")) {
            selected = listWord.getSelectionModel().getSelectedItem();
        } else if (currentTab.equals("Recent")) {
            selected = listWordRecent.getSelectionModel().getSelectedItem();
        } else if (currentTab.equals("Favourite")) {
            selected = listWordFavorite.getSelectionModel().getSelectedItem();
        }

        if(selected != null) {

            String query = null;

            if (currentTab.equals("Search") || currentTab.equals("Recent")) {
                query = "UPDATE av SET favorite = 1 WHERE word = ?";
            } else if (currentTab.equals("Favourite")) {
                query = "UPDATE av SET favorite = 0 WHERE word = ?";
            }

            try {
                st = con.prepareStatement(query);
                st.setString(1,selected);
                st.executeUpdate();
                st.close();

                refreshListWordFavorite();

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        else{
            showAlert.AlertInfo("Please choose a word which you favorite");
        }

    }

    @FXML
    public void clickEditWord(ActionEvent e) throws IOException {
        String currentTab = tabPane.getSelectionModel().getSelectedItem().getText();
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/htmlEditor.fxml"));
        Parent SceneChange = loader.load();
        Scene scene = new Scene(SceneChange);

        controllerEditor controllerEditor = loader.getController();
        if (currentTab.equals("Search")) {
            controllerEditor.setWord(listWord.getSelectionModel().getSelectedItem());
        } else if (currentTab.equals("Recent")) {
            controllerEditor.setWord(listWordRecent.getSelectionModel().getSelectedItem());
        } else if (currentTab.equals("Favourite")) {
            controllerEditor.setWord(listWordFavorite.getSelectionModel().getSelectedItem());
        }

        if(controllerEditor.getWord() != null) {
            stage.setScene(scene);
        }
        else{
            showAlert.AlertInfo("Please choose a word which you want to edit!!");
        }
    }

    @FXML
    public void clickGoogleTranslate(ActionEvent e) throws IOException {
        changeScene(e,"../fxml/GoogleTranslate.fxml");
    }

    @FXML
    public void clickAddWord(ActionEvent e) throws IOException {
        changeScene(e,"../fxml/AddWord.fxml");
    }

    @FXML
    public void logIn() throws IOException {
        Stage stage = (Stage)menuBar.getScene().getWindow();
        Parent SceneChange = FXMLLoader.load(getClass().getResource("../fxml/LogIn.fxml"));
        Scene scene = new Scene(SceneChange);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webEngine = webView.getEngine();
        loadDatabase();
        handleSelectEvent(listWord);
        handleSelectEvent(listWordFavorite);
        handleSelectEvent(listWordRecent);
    }
}
