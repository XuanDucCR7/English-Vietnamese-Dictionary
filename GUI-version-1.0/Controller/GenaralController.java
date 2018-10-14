package Controller;

import Dictionary.DatabaseConnection;
import Dictionary.showAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenaralController {

    protected Connection con = DatabaseConnection.getConnection();
    protected PreparedStatement st;
    protected ResultSet rs;

    protected ObservableList<String> items = FXCollections.observableArrayList();
    protected ObservableList<String> favoriteWord = FXCollections.observableArrayList();
    protected FilteredList<String> filteredItems = new FilteredList<>(items, e -> true);

    protected void refreshDatabase() {
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

    protected void refreshListWordFavorite(){
        String query1 = "SELECT word FROM av WHERE favorite = ? ";
        try{
            st = con.prepareStatement(query1);
            st.setString(1,"1");
            rs = st.executeQuery();

            favoriteWord.clear();
            while (rs.next()) {
                favoriteWord.add(rs.getString(1));
            }
            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void deleteWord(ListView<String> listWord, WebEngine webEngine){
        try {
            String selected = listWord.getSelectionModel().getSelectedItem();
            if(selected != null){
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
            }
            else{
                showAlert.AlertInfo("Please choose a word you want to delete!!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void addWord(String Word, String Explain){
        String query = "INSERT INTO av (word, html) VALUES(?,?)";
        try{
            st = con.prepareStatement(query);
            st.setString(1, Word);
            st.setString(2, Explain);
            st.execute();
            st.close();

            refreshDatabase();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    protected void changeScene(ActionEvent e, String url) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        Parent SceneChange = FXMLLoader.load(getClass().getResource(url));
        Scene scene = new Scene(SceneChange);
        stage.setScene(scene);
        stage.show();
    }
}
