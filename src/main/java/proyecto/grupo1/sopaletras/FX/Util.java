package proyecto.grupo1.sopaletras.FX;

import java.io.IOException;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import javafx.scene.image.ImageView;

import static proyecto.grupo1.sopaletras.Main.loadFXML;

public class Util {
    public static void mostrarYuca(Pane pane) {
        final double width = pane.getWidth();
        final double height = pane.getHeight();

        final ImageView img = new ImageView(
                Util.class.getResource("/images/yuca.png").toExternalForm());
        img.setFitWidth(200);
        img.setPreserveRatio(true);

        img.setX(0);
        img.setY(height);

        pane.getChildren().add(img);

        Thread th = new Thread(() -> {
            while (img.getX() < width && img.getY() > 0) {
                try {
                    Platform.runLater(() -> {
                        img.setX(img.getX() + 20);
                        img.setY(img.getY() - 20);
                    });
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
            }
            Platform.runLater(() -> pane.getChildren().remove(img));
        });
        th.setDaemon(true);
        th.start();
    }

    public static void cargarVistaInicio(Scene scene) {
        try {
            Parent newRoot = loadFXML("/fxml/jugar.fxml");
            Scene newScene = new Scene(newRoot);
            Stage theStage = (Stage)scene.getWindow();
            theStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
