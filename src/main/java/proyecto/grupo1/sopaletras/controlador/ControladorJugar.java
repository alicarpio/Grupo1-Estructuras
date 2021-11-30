package proyecto.grupo1.sopaletras.controlador;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class ControladorJugar {
    @FXML
    private ComboBox<String> comboDim;
     @FXML
    private ComboBox<String> comboTema;
    @FXML
    private Button btnJugar;
    @FXML
    private Button btnExtreme;

    @FXML
    private void initialize() {
        comboDim.getItems().add("10 x 10");
        comboDim.getItems().add("12 x 12");
        comboDim.getItems().add("15 x 15");
        comboDim.getItems().add("18 x 18");
        comboDim.getItems().add("20 x 20");
        comboDim.setValue("12 x 12");

        comboTema.getItems().add("ANIMALES");
        comboTema.getItems().add("CIUDADES");
        comboTema.getItems().add("HARRY-POTTER");
        comboTema.getItems().add("MARVEL");
        comboTema.getItems().add("NUMEROS");
        comboTema.getItems().add("DC");
        comboTema.setValue("ANIMALES");
    }

    private int parseTamanioSopa() {
        return Integer.parseInt(comboDim.getValue().split("x")[0].trim());
    }

    private String parseTemaSopa() {
        return comboTema.getValue().toLowerCase();
    }

    @FXML
    private void onJugar(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sopaletras.fxml"));

        ControladorSopa controladorSopa =
            new ControladorSopa(parseTamanioSopa(), parseTemaSopa(), e.getTarget() == btnExtreme);

        loader.setController(controladorSopa);
        Parent root = loader.load();
        Scene oldScene = btnJugar.getScene();
        Stage theStage = (Stage)oldScene.getWindow();
        theStage.setScene(new Scene(root, oldScene.getWidth(), oldScene.getHeight()));
    }

    @FXML
    private void onSalir(ActionEvent e) {
        Platform.exit();
    }
}
