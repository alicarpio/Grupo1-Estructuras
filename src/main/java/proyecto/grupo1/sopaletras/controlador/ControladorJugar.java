package proyecto.grupo1.sopaletras.controlador;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.application.Platform;

import java.io.IOException;

import static proyecto.grupo1.sopaletras.Main.loadFXML;

public class ControladorJugar {
    @FXML
    private ComboBox<String> comboDim;
    @FXML
    private Button btnJugar;

    @FXML
    private void initialize() {
        comboDim.getItems().add("10 x 10");
        comboDim.getItems().add("12 x 12");
        comboDim.getItems().add("15 x 15");
        comboDim.getItems().add("18 x 18");
        comboDim.getItems().add("20 x 20");
        comboDim.setValue("12 x 12");
    }

    private int parseTamanioSopa() {
        return Integer.parseInt(comboDim.getValue().split("x")[0].trim());
    }

    @FXML
    private void onJugar(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sopaletras.fxml"));
        ControladorSopa controladorSopa = new ControladorSopa(parseTamanioSopa());
        loader.setController(controladorSopa);
        Parent root = loader.load();
        Scene oldScene = btnJugar.getScene();
        Stage theStage = (Stage)oldScene.getWindow();
        theStage.setScene(new Scene(root, oldScene.getWidth(), oldScene.getHeight()));
    }
}
