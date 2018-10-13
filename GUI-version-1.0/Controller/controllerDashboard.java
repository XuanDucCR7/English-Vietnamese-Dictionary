package Controller;

import Dictionary.TextToSpeak;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class controllerDashboard extends GenaralController implements Initializable {
    @FXML
    private TextField search;
    @FXML
    private ListView<String> listWord;
    @FXML
    private ListView<String> listWordFavorite;
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

        String query1 = "SELECT word FROM av WHERE favorite = ? ";
        try{
            st = con.prepareStatement(query1);
            st.setString(1,"1");
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
    }

    @FXML
    private void handleDeleteEvent() {
        deleteWord(listWord, webEngine);
    }

    private void displaySelectedItem() {
        String query = "SELECT html FROM av WHERE word=?";

        try {
            st = con.prepareStatement(query);
            st.setString(1, listWord.getSelectionModel().getSelectedItem());
            rs = st.executeQuery();

            while (rs.next()) {
                webEngine.loadContent(rs.getString(1));
            }
            st.close();
            rs.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private void handleSelectEvent() {
        listWord.setOnMouseClicked(e -> {
            displaySelectedItem();
        });

        listWord.setOnKeyPressed(e -> {
            KeyCode keyCode = e.getCode();
            if (keyCode == KeyCode.ENTER) {
                displaySelectedItem();
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

    public void clickGoogleTranslate(ActionEvent e) throws IOException {
        changeScene(e,"../fxml/GoogleTranslate.fxml");
    }

    public void clickAddWord(ActionEvent e) throws IOException {
        changeScene(e,"../fxml/AddWord.fxml");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webEngine = webView.getEngine();
        loadDatabase();
        handleSelectEvent();
    }
}
