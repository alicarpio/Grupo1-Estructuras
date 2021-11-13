package proyecto.grupo1.sopaletras.controlador;

import proyecto.grupo1.sopaletras.modelo.Cell;
import proyecto.grupo1.sopaletras.modelo.Direction;

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
        return getDirection() != Direction.NONE;
    }

    public Direction getDirection() {
        int x1 = selectionStart.getRow();
        int y1 = selectionStart.getCol();
        int x2 = selectionEnd.getRow();
        int y2 = selectionEnd.getCol();

        if (x1 == x2) {
            return Direction.ROW;
        }
        else if (y1 == y2) {
            return Direction.COL;
        }
        else if (Math.abs(x2-x1) == Math.abs(y2-y1)) {
            return Direction.DIAG;
        }
        else {
            return Direction.NONE;
        }
    }
}
