package proyecto.grupo1.sopaletras.controlador;

import proyecto.grupo1.sopaletras.modelo.Cell;

public class SelectionState {
    private Cell selectionStart;
    private Cell selectionEnd;

    public SelectionState(Cell selectionStart) {
        this.selectionStart = selectionStart;
    }

    public void setSelectionEnd(Cell selectionEnd) {
        this.selectionEnd = selectionEnd;
    }

    public Cell getSelectionStart() {
        return selectionStart;
    }

    public Cell getSelectionEnd() {
        return selectionEnd;
    }

    /**
     * Returns if the current selection is valid.
     *
     * A selection is valid if one of the following is true:
     * - The start and end cells are in the same row
     * - The start and end cells are in the same column
     * - The start and end cells are in the same diagonal
     *
     * Further validation to check that the word was not already selected must
     * be done.
     * @return true is the selection is valid, false otherwise
     */
    public boolean isValid() {
        // TODO
        return false;
    }
}
