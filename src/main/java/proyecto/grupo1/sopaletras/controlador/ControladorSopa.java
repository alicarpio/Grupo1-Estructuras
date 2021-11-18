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
import java.util.Optional;

import proyecto.grupo1.sopaletras.modelo.SopaLetras;
import proyecto.grupo1.sopaletras.modelo.Cell;
import proyecto.grupo1.sopaletras.DS.List;
import static proyecto.grupo1.sopaletras.FX.Util.*;

public class ControladorSopa {
    @FXML private Label lblPuntos;
    @FXML private Label lblErrores;
    @FXML private VBox panelPalabrasValidas;
    @FXML private GridPane tableroJuego;

    @FXML private Button btnShiftLeft;
    @FXML private Button btnShiftRight;
    @FXML private Button btnAddColumn;
    @FXML private Button btnAddRow;

    @FXML private ComboBox<Integer> comboN;

    final static int MAX_ERRORES = 3;
    private SopaLetras sopaLetras;
    private SelectionState selectionState;

    public ControladorSopa(int dimensions) {
        try {
            sopaLetras = new SopaLetras(dimensions);
        } catch (Exception ex) {
            notifyError(ex.getMessage(), true);
            ex.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        actualizarComboN();
        actualizarPalabrasValidas();
        actualizarTablero();
    }

    private void actualizarComboN() {
        // TODO: se necesita uno pa las filas y otro pa las columnas
        Integer[] N = new Integer[sopaLetras.getTablero().size()];
        for (int i = 0; i < N.length; i++) N[i] = i+1;
        comboN.getItems().addAll(N);
        comboN.setValue(N[0]);
    }

    private void actualizarTablero() {
        tableroJuego.getChildren().clear();
        int N = sopaLetras.getTablero().size();
        var tablero = sopaLetras.getTablero();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Cell cell = tablero.get(i).get(j);
                Text t = makeText(String.valueOf(cell.getLetter()), N);
                StackPane pane = new StackPane(t);
                pane.setAlignment(Pos.CENTER);
                if (cell.isMarked())
                    pane.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, null, null)));
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
            if (!selectionState.isValid()) selectionState.getSelectionStart().setMarked(false);
            else                           marcarPalabra();
            selectionState = null;
        }
        actualizarTablero();
    }

    private void marcarPalabra() {
        String palabra =
            sopaLetras.mark(selectionState.getSelectionStart()
                          , selectionState.getSelectionEnd()
                          , selectionState.getDirection());

        // Si se marco una palabra valida, la removemos de la sopa y actualizamos el puntaje
        if (sopaLetras.valida(palabra)) {
            sopaLetras.remove(palabra);
            incrementarPuntaje(palabra);
            actualizarPalabrasValidas();
        }
        else {
            notifyError("Palabra no valida: " + palabra);
            decrementarPuntaje(palabra);
        }

        // Caso contrario, si la palabra no ha sido marcada con
        // anterioridad desmarcamos la primera celda de la seleccion
        if (!sopaLetras.marcada(palabra) 
                && !selectionState.getSelectionEnd().isMarked()
                || selectionState.getSelectionStart() == selectionState.getSelectionEnd()) {
            selectionState.getSelectionStart().setMarked(false);
        }
    }

    private void incrementarPuntaje(String palabra) {
        int puntosGanados  = palabra.length();
        int puntosActuales = Integer.parseInt(lblPuntos.getText());
        lblPuntos.setText(String.valueOf(puntosActuales + puntosGanados));
    }

    private void decrementarPuntaje(String palabra) {
        int puntosPerdidos  = palabra.length();
        int erroresActuales = Integer.parseInt(lblErrores.getText());
        if (erroresActuales + 1 > MAX_ERRORES)
            notifyError("Maximo numero de errores alcanzado!", true);
        int puntosActuales  = Integer.parseInt(lblPuntos.getText());
        lblPuntos.setText(String.valueOf(puntosActuales - puntosPerdidos));
        lblErrores.setText(String.valueOf(erroresActuales + 1));
    }

    private void actualizarPalabrasValidas() {
        panelPalabrasValidas.getChildren().clear();
        List<String> palabrasValidas = sopaLetras.getPalabrasValidas();
        for (String palabra : palabrasValidas) {
            panelPalabrasValidas.getChildren().add(new Label(palabra));
        }
    }

    @FXML
    private void rotarIzquierda() { rotar("left"); }

    @FXML
    private void rotarDerecha() { rotar("right"); }

    /**
     * Rota una fila del tablero una posicion en la direccion indicada.
     * @param direccion la direccion en la que se debe rotar la fila
     */
    private void rotar(String direccion) {
        int row = comboN.getValue();
        sopaLetras.rotarFila(direccion, row);
        actualizarTablero();
    }
}
