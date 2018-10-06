package Controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class controllerDashboard implements Initializable {
    @FXML
    private TextField search;
    @FXML
    private ListView<String> listWord;

    private ObservableList<String> list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableArrayList("minh","minh2");
        listWord.setItems(list);
    }
}
