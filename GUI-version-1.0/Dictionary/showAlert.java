package Dictionary;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class showAlert {
    public static void AlertInfo(String info){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(info);
        alert.setTitle("Information");
        alert.show();
    }

    public static String AlertLogIn(boolean IsLogIn){
        String message = "";
        if(IsLogIn == false){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("You need to log in to use this function");
            alert.setContentText("Choose your option");

            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            Optional<ButtonType> result = alert.showAndWait();
            message = result.get().getText();
        }
        return message;
    }
}
