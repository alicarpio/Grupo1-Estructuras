package proyecto.grupo1.sopaletras.modelo;

public class Cell {
    private char letter;
    private boolean marked;

    public Cell(char letter) {
        this.letter = letter;
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
}
