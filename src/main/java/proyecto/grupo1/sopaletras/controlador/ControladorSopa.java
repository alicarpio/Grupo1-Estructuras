package proyecto.grupo1.sopaletras.controlador;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.io.IOException;

import proyecto.grupo1.sopaletras.modelo.SopaLetras;
import proyecto.grupo1.sopaletras.modelo.Cell;
import proyecto.grupo1.sopaletras.DS.List;

public class ControladorSopa {
    @FXML
    private Label lblPuntos;
    @FXML
    private VBox panelPalabrasValidas;
    @FXML
    private GridPane tableroJuego;

    private int dimensions;
    private SopaLetras sopaLetras;

    public ControladorSopa(int dimensions) {
        this.dimensions = dimensions;
    }

    @FXML
    private void initialize() {
        System.out.println("Incializando tablero con N=:" + dimensions);
        try {
            sopaLetras = new SopaLetras(dimensions);
            anadirPalabrasValidas();
        } catch (Exception e) {
            crashearElegantemente(e.getMessage());
        }
    }

    private void anadirPalabrasValidas() {
        List<String> palabrasValidas = sopaLetras.getPalabrasValidas();
        for (String palabra : palabrasValidas) {
            panelPalabrasValidas.getChildren().add(new Label(palabra));
        }
    }

    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }

    private void crashearElegantemente(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.showAndWait();
        Platform.exit();
    }
}
