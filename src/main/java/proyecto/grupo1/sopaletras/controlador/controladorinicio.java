package proyecto.grupo1.sopaletras.controlador;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.application.Platform;

import java.io.IOException;

import static proyecto.grupo1.sopaletras.Main.loadFXML;

public class ControladorInicio {
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnJugar;

    @FXML
    private void onJugar(ActionEvent e) throws IOException {
        Parent newRoot = loadFXML("/fxml/jugar.fxml");
        Scene oldScene = btnSalir.getScene();
        Scene newScene = new Scene(newRoot, oldScene.getWidth(), oldScene.getHeight());
        Stage theStage = (Stage)oldScene.getWindow();
        theStage.setScene(newScene);
    }

    @FXML
    private void onSalir(ActionEvent e) {
        Platform.exit(); 
    }
}
