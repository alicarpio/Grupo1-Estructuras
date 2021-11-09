package proyecto.grupo1.sopaletras.modelo;

public class Cell {
    private char letter;
    private boolean marked;
    private int row;
    private int col;

    public Cell(char letter, int row, int col) {
        this.letter = letter;
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getLetter() {
        return letter;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
