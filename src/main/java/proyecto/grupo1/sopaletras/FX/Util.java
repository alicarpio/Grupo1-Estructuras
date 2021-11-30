package proyecto.grupo1.sopaletras.FX;

import javafx.scene.*;
import javafx.stage.*;
import javafx.application.Platform;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicReference;

import proyecto.grupo1.sopaletras.DS.List;

public class Util {
    public static Text makeText(String text, int size) {
        Text t = new Text(text);
        t.setFont(Font.font(null, FontWeight.BOLD, 280.0 / size));
        return t;
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

    public static void showMessage(String msg, boolean shouldExit) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
        alert.showAndWait();
        if (shouldExit)
            Platform.exit();
    }

    public static void showMessage(String msg) {
        showMessage(msg, false);
    }

    @SafeVarargs
    public static <T> T makeComboPopup(String title, T... options) {
        Stage popup = new Stage();
        VBox root = new VBox(10);
        AtomicReference<T> selection = new AtomicReference<>();

        ComboBox<T> combo = new ComboBox<>();
        combo.getItems().addAll(options);
        combo.setValue(options[0]);

        Button btn = new Button("Ok");
        btn.setOnAction(e -> {
            selection.set(combo.getValue());
            popup.close();
        });

        root.getChildren().addAll(combo, btn);
        Scene scene = new Scene(root, 200, 200);

        popup.setScene(scene);
        popup.setTitle(title);
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.showAndWait();
        return selection.get();
    }
}
