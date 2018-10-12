package Controller;

import Dictionary.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class controllerDashboard implements Initializable {

    private Connection con = DatabaseConnection.getConnection();

    private PreparedStatement st;
    private ResultSet rs;

    @FXML
    private TextField search;
    @FXML
    private ListView<String> listWord;
    @FXML
    private WebView webView;
    private WebEngine webEngine;

    private ObservableList<String> items = FXCollections.observableArrayList();
    private FilteredList<String> filteredItems = new FilteredList<>(items, e -> true);

    private void loadDatabase() {
        String query = "SELECT word FROM av";
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                items.add(rs.getString(1));
                listWord.setItems(items);
            }
            st.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshDatabase() {
        String query = "SELECT word FROM av";
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            items.clear();
            while (rs.next()) {
                items.add(rs.getString(1));
            }
            st.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDeleteEvent() {
        try {
            String selected = listWord.getSelectionModel().getSelectedItem();
            System.out.println(selected);

            if (selected != null) {
                String query = "DELETE FROM av WHERE word=?";
                st = con.prepareStatement(query);
                st.setString(1, selected);
                st.executeUpdate();

                st.close();
                rs.close();

                refreshDatabase();
                webEngine.loadContent("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void clickGoogleTranslate(ActionEvent e) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        Parent GoogleTranslateView = FXMLLoader.load(getClass().getResource("../fxml/GoogleTranslate.fxml"));
        Scene scene = new Scene(GoogleTranslateView);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webEngine = webView.getEngine();
        loadDatabase();
        handleSelectEvent();
    }
}
