package proyecto.grupo1.sopaletras.FX;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

    public static void showMessage(String title, String msg, boolean shouldExit) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.showAndWait();
        if (shouldExit)
            Platform.exit();
    }

    public static void showMessage(String title, String msg) {
        showMessage(title, msg, false);
    }

    public static String input(String prompt) {
        Stage popup = new Stage();
        popup.setResizable(false);
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        Label lbl = new Label(prompt);
        lbl.setFont(Font.font("System", FontWeight.NORMAL, 18));

        TextField txtField = new TextField();
        txtField.setPrefHeight(50);
        txtField.setPrefWidth(100);
        txtField.setAlignment(Pos.CENTER);
        txtField.setFont(Font.font("System", FontWeight.NORMAL, 18));
        
        HBox hbox = new HBox(20, lbl, txtField);
        hbox.setAlignment(Pos.CENTER);

        Button btn = new Button("Cambiar");
        btn.setPrefHeight(50);
        btn.setPrefWidth(200);
        btn.setFont(Font.font("System", FontWeight.NORMAL, 24));
        btn.setOnAction(e -> popup.close());

        root.getChildren().addAll(hbox, btn);
        Scene scene = new Scene(root, 300, 200);

        popup.setScene(scene);
        popup.setTitle("Input");
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.showAndWait();

        return txtField.getText();
    }
    
}
