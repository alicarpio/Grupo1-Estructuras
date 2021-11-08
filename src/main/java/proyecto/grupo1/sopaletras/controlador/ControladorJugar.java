package proyecto.grupo1.sopaletras.controlador;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.application.Platform;

import java.io.IOException;

import static proyecto.grupo1.sopaletras.Main.loadFXML;

public class ControladorJugar {
    @FXML
    private TextField txtTamanioSopa;
    @FXML
    private Button btnJugar;

    @FXML
    private void onJugar(ActionEvent e) throws IOException {
        String tamanioSopa = txtTamanioSopa.getText().trim();
        if (!tamanioSopa.matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor ingrese un numero entero");
            alert.showAndWait();
        }
        else {
            int tamanio = Integer.parseInt(tamanioSopa);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sopaletras.fxml"));
            ControladorSopa controladorSopa = new ControladorSopa(tamanio);
            loader.setController(controladorSopa);
            Parent root = loader.load();
            Scene oldScene = btnJugar.getScene();
            Stage theStage = (Stage)oldScene.getWindow();
            theStage.setScene(new Scene(root, oldScene.getWidth(), oldScene.getHeight()));
        }
    }
}
