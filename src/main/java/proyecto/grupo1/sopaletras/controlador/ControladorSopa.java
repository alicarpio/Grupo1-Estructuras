package proyecto.grupo1.sopaletras.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.application.Platform;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;

import java.io.IOException;

import proyecto.grupo1.sopaletras.modelo.SopaLetras;
import proyecto.grupo1.sopaletras.modelo.Cell;
import proyecto.grupo1.sopaletras.DS.List;
import static proyecto.grupo1.sopaletras.FX.Util.*;

public class ControladorSopa {
    @FXML
    private Label lblPuntos;
    @FXML
    private VBox panelPalabrasValidas;
    @FXML
    private GridPane tableroJuego;

    @FXML private Button btnShiftLeft;
    @FXML private Button btnShiftRight;
    @FXML private Button btnAddColumn;
    @FXML private Button btnAddRow;

    @FXML private ComboBox<Integer> comboRow;

    private int dimensions;
    private SopaLetras sopaLetras;
    private SelectionState selectionState;

    public ControladorSopa(int dimensions) {
        this.dimensions = dimensions;
    }

    @FXML
    private void initialize() {
        System.out.println("Incializando tablero con N=:" + dimensions);
        try {
            sopaLetras = new SopaLetras(dimensions);
            llenarComboRow();
            anadirPalabrasValidas();
            actualizarTablero();
        } catch (Exception e) {
            crashearElegantemente(e.getMessage());
        }
    }

    private void llenarComboRow() {
        Integer[] rows = new Integer[dimensions];
        for (int i = 0; i < rows.length; i++) rows[i] = i+1;
        comboRow.getItems().addAll(rows);
        comboRow.setValue(rows[0]);
    }

    private void actualizarTablero() {
        tableroJuego.getChildren().clear();
        var tablero = sopaLetras.getTablero();
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                Cell cell = tablero.get(i).get(j);
                Text t = makeText(String.valueOf(cell.getLetter()), dimensions);
                StackPane pane = new StackPane(t);
                pane.setAlignment(Pos.CENTER);
                if (cell.isMarked()) {
                    pane.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, null, null)));
                }
                pane.setOnMouseClicked(e -> handleMouseClick(cell));
                GridPane.setMargin(pane, new Insets(5, 10, 5, 10));
                tableroJuego.add(pane, j, i);
            }
        }
    }

    private void handleMouseClick(Cell cell) {
        if (selectionState == null) {
            selectionState = new SelectionState(cell);
            cell.setMarked(true);
        }
        else {
            selectionState.setSelectionEnd(cell);
            if (selectionState.isValid()) {
                // TODO: marcar toda la palabra
                cell.setMarked(true);
            }
            else {
                selectionState.getSelectionStart().setMarked(false);
            }
            selectionState = null;
        }
        actualizarTablero();
    }

    private void anadirPalabrasValidas() {
        List<String> palabrasValidas = sopaLetras.getPalabrasValidas();
        for (String palabra : palabrasValidas) {
            panelPalabrasValidas.getChildren().add(new Label(palabra));
        }
    }

    @FXML
    private void rotarIzquierda() {
        rotar("left");
    }

    @FXML
    private void rotarDerecha() {
        rotar("right");
    }

    private void rotar(String direccion) {
        int row = comboRow.getValue();
        switch (direccion) {
            case "left":
                sopaLetras.getTablero().get(row-1).shiftLeft();
                break;
            case "right":
                sopaLetras.getTablero().get(row-1).shiftRight();
                break;
            default:
                throw new RuntimeException("Invalid rotation direction: " + direccion);
        }
        actualizarTablero();
    }

    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }
}
