package Controller;

import Dictionary.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

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

    private void loadDatabase() {
        String query = "SELECT word FROM tbl_edict";
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

    private void handleSelectEvent() {
        listWord.setOnMouseClicked(e -> {
            String query = "SELECT * FROM tbl_edict WHERE word=?";

            try {
                st = con.prepareStatement(query);
                st.setString(1, listWord.getSelectionModel().getSelectedItem());
                rs = st.executeQuery();

                while (rs.next()) {
                    webEngine.loadContent(rs.getString("detail"));
                }
                st.close();
                rs.close();

            } catch (SQLException se) {
                se.printStackTrace();
            }
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webEngine = webView.getEngine();
        loadDatabase();
        handleSelectEvent();
    }
}
