package proyecto.grupo1.sopaletras.FX;

import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class Util {
    public static void notifyError(String msg, boolean shouldExit) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.showAndWait();
        if (shouldExit)
            Platform.exit();
    }

    public static void notifyError(String msg) {
        notifyError(msg, false);
    }

    public static void showMessage(String msg, boolean shouldExit) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
        alert.showAndWait();
        if (shouldExit)
            Platform.exit();
    }

    public static void showMessage(String msg) {
        showMessage(msg, false);
    }

    public static String input(String prompt) {
        Stage popup = new Stage();
        VBox root = new VBox(10);

        Label lbl = new Label(prompt);
        TextField txtField = new TextField();
        HBox hbox = new HBox(20, lbl, txtField);

        Button btn = new Button("Ok");
        btn.setOnAction(e -> popup.close());

        root.getChildren().addAll(hbox, btn);
        Scene scene = new Scene(root, 200, 200);

        popup.setScene(scene);
        popup.setTitle("Input");
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.showAndWait();

        return txtField.getText();
    }
    
}
