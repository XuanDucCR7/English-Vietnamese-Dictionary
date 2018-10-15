package Controller;

import Dictionary.TextToSpeak;
import Dictionary.showAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
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
            st = con.prepareStatement(query1);;
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
        deleteWord(listWord, webEngine);
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

                System.out.println(maxValue);

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
    public void TextToSpeak(ActionEvent e){
        String Text = listWord.getSelectionModel().getSelectedItem();
        TextToSpeak.playSound(Text);
    }

    public void addFavoriteWord(){
        String selected = listWord.getSelectionModel().getSelectedItem();
        if(selected != null){
            String query = "UPDATE av SET favorite = ? WHERE word = ?";
            try{
                st = con.prepareStatement(query);
                st.setString(1,"1");
                st.setString(2,selected);
                st.executeUpdate();
                st.close();


            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            refreshListWordFavorite();
        }
        else{
            showAlert.AlertInfo("Please choose a word which you favorite");
        }

    }

    public void clickEditWord(ActionEvent e) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/htmlEditor.fxml"));
        Parent SceneChange = loader.load();
        Scene scene = new Scene(SceneChange);

        controllerEditor controllerEditor = loader.getController();
        if(listWord.getSelectionModel().getSelectedItem() != null){
            controllerEditor.setWord(listWord.getSelectionModel().getSelectedItem());
            stage.setScene(scene);
        }
        else{
            showAlert.AlertInfo("Please choose a word which you want to edit!!");
        }

    }

    public void clickGoogleTranslate(ActionEvent e) throws IOException {
        changeScene(e,"../fxml/GoogleTranslate.fxml");
    }

    public void clickAddWord(ActionEvent e) throws IOException {
        changeScene(e,"../fxml/AddWord.fxml");
    }

    public void logIn(ActionEvent e) throws IOException {
        changeScene(e, "../fxml/LogIn.fxml");
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
