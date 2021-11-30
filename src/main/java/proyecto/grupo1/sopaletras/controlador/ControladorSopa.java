package proyecto.grupo1.sopaletras.controlador;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.input.MouseButton;

import proyecto.grupo1.sopaletras.DS.List;
import proyecto.grupo1.sopaletras.FX.Util;
import static proyecto.grupo1.sopaletras.FX.Util.*;
import proyecto.grupo1.sopaletras.modelo.Cell;
import proyecto.grupo1.sopaletras.modelo.SopaLetras;

public class ControladorSopa {
    @FXML private Label lblPuntos;
    @FXML private Label lblErrores;

    @FXML private Label lblTiempo;
    @FXML private VBox  vboxTiempo;

    @FXML private VBox panelPalabrasValidas;
    @FXML private GridPane tableroJuego;
    @FXML private BorderPane rootPane;

    @FXML private Button btnShiftLeft;
    @FXML private Button btnShiftRight;
    @FXML private Button btnAddColumn;
    @FXML private Button btnAddRow;
    @FXML private Button btnDeleteColumn;
    @FXML private Button btnDeleteRow;
    @FXML private Button btnShiftUp;
    @FXML private Button btnShiftDown;

    @FXML private ComboBox<Integer> comboRows;
    @FXML private ComboBox<Integer> comboCols;

    final static int MAX_ERRORES = 3;
    final static int MAX_MODIFICACIONES = 2;

    private boolean extreme;
    private int numeroModificaciones;
    private SopaLetras sopaLetras;
    private SelectionState selectionState;


    public ControladorSopa(int N, String tema, boolean extreme, String idioma ) {
         try {
            sopaLetras = new SopaLetras(N, N, tema,idioma);
            this.extreme = extreme;
        } catch (Exception ex) {
            notifyError(ex.getMessage(), true);
            ex.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        actualizarComboRows();
        actualizarComboCols();
        actualizarPalabrasValidas();
        actualizarTablero();
        if (extreme) {
            new Thread(this::timer).start();
        }
        else {
            rootPane.getChildren().remove(vboxTiempo);
        }
    }

    private void timer() {
        while (true) {
            try {
                int tiempo = Integer.parseInt(lblTiempo.getText());
                if (tiempo <= 0) break;
                Platform.runLater(() -> lblTiempo.setText(String.valueOf(tiempo - 1)));
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
        Platform.runLater(() -> showMessage("Se te acabo el tiempo jaja xd", true));
    }

    private void actualizarComboN(ComboBox<Integer> combo, int N) {
        combo.getItems().clear();
        for (int i = 0; i < N; i++)
            combo.getItems().add(i+1);
        combo.setValue(1);
    }

    private void actualizarComboRows() {
        actualizarComboN(comboRows, sopaLetras.getTablero().size());
    }

    private void actualizarComboCols() {
        actualizarComboN(comboCols, sopaLetras.getTablero().get(0).size());
    }

    private void actualizarTablero() {
        tableroJuego.getChildren().clear();
        int rows = sopaLetras.getTablero().size();
        int cols = sopaLetras.getTablero().get(0).size();
        var tablero = sopaLetras.getTablero();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = tablero.get(i).get(j);
                Text t = new Text(String.valueOf(cell.getLetter()));
                t.setFont(Font.font(null, FontWeight.BOLD, 280.0 / rows));
                StackPane pane = new StackPane(t);
                pane.setAlignment(Pos.CENTER);
                if (cell.isMarked())
                    pane.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, null, null)));
                pane.setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.SECONDARY) {
                        if (!cell.isMarked()) {
                            String newLetter = input("Ingrese la letra nueva");
                            cell.setLetter(newLetter.charAt(0));
                            actualizarTablero();
                        }
                    }
                    else {
                        handleMouseClick(cell);
                    }
                });
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
            if (!selectionState.isValid())
                selectionState.getSelectionStart().setMarked(false);
            else
                marcarPalabra();
            selectionState = null;
        }
        actualizarTablero();
    }

    private void marcarPalabra() {
        String palabra = sopaLetras.mark(selectionState.getSelectionStart(),
                selectionState.getSelectionEnd(), selectionState.getDirection());

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
        if (erroresActuales >= MAX_ERRORES)
            notifyError("Maximo numero de errores alcanzado!", true);
        int puntosActuales  = Integer.parseInt(lblPuntos.getText());
        lblPuntos.setText(String.valueOf(puntosActuales - puntosPerdidos));
        lblErrores.setText(String.valueOf(erroresActuales + 1));
    }

    private void actualizarPalabrasValidas() {
        panelPalabrasValidas.getChildren().clear();
        List<String> palabrasValidas = sopaLetras.getPalabrasValidas();
        List<String> palabrasMarcadas = sopaLetras.getPalabrasMarcadas();
        for (String palabra : palabrasValidas) {
            Text t = new Text(palabra);
            t.setFont(Font.font(24));
            panelPalabrasValidas.getChildren().add(t);
        }
        for (String palabra : palabrasMarcadas) {
            Text t = new Text(palabra);
            t.setFont(Font.font(24));
            t.setStrikethrough(true);
            panelPalabrasValidas.getChildren().add(t);
        }
    }

    @FXML
    private void rotar(ActionEvent e) {
        sopaLetras.rotarFila(e.getTarget() == btnShiftRight ? "right" : "left",
                comboRows.getValue() - 1);
        actualizarTablero();
    }

    @FXML
    private void rotarVert(ActionEvent e) {
        sopaLetras.rotarCol(e.getTarget() == btnShiftUp ? "up" : "down",
                comboCols.getValue());
        actualizarTablero();
    }

    @FXML
    private void anadir(ActionEvent e) {
        if (!(numeroModificaciones < MAX_MODIFICACIONES)) {
            Util.notifyError("Limite de Añadir/Eliminar Alcanzado");
            return;
        }

        if (e.getTarget() == btnAddColumn) {
            sopaLetras.anadirColumna();
            actualizarComboCols();
        }
        else {
            sopaLetras.anadirFila();
            actualizarComboRows();
        }

        numeroModificaciones++;
        actualizarTablero();
    }

    @FXML
    private void eliminar(ActionEvent e) {
        if (!(numeroModificaciones < MAX_MODIFICACIONES)) {
            Util.notifyError("Limite de Añadir/Eliminar Alcanzado");
            return;
        }

        if (e.getTarget() == btnDeleteRow) {
            sopaLetras.eliminarFila(comboRows.getValue() - 1);
            actualizarComboRows();
        }
        else {
            sopaLetras.eliminarColumna(comboCols.getValue() - 1);
            actualizarComboCols();
        }
        numeroModificaciones++;
        actualizarTablero();
    }

    @FXML
    private void onSalir(ActionEvent e) {
        Platform.exit();
    }
}
