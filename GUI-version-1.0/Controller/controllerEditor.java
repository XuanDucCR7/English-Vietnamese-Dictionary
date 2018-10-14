package Controller;

import Dictionary.showAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.web.HTMLEditor;

import java.io.IOException;
import java.sql.SQLException;

public class controllerEditor extends GenaralController{
    @FXML
    private TextArea Word;

    @FXML
    private HTMLEditor htmlEditor;

    private int id;

    public void setWord(String word){
        Word.setText(word);
        String query = "SELECT id,html FROM av WHERE word = ?";
        try{
            st = con.prepareStatement(query);
            st.setString(1,word);
            rs = st.executeQuery();

            while (rs.next()) {
                htmlEditor.setHtmlText(rs.getString(2));
                id = Integer.parseInt(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateWord(ActionEvent e) throws IOException {
        String word = Word.getText();
        String html = htmlEditor.getHtmlText();
        String query = "UPDATE av SET word = ? , html = ? WHERE id = ?";
        try{
            st = con.prepareStatement(query);
            st.setString(1,word);
            st.setString(2,html);
            st.setInt(3,id);
            st.executeUpdate();
            st.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        refreshDatabase();
        showAlert.AlertInfo("Your your has been edited!");
        changeScene(e, "../fxml/Dashboard.fxml");

    }

    public void cancel(ActionEvent e) throws IOException {
        changeScene(e, "../fxml/Dashboard.fxml");
    }
}
