package proyecto.grupo1.sopaletras.modelo;

import java.io.IOException;

import proyecto.grupo1.sopaletras.DS.List;
import proyecto.grupo1.sopaletras.DS.Vector;
import proyecto.grupo1.sopaletras.DS.CircularList;

public class SopaLetras {
    private List<CircularList<Cell>> tablero;
    private List<String> palabrasValidas;
    private List<String> palabrasMarcadas;
    private int rows, cols;

    // Necesitamos estas dos para cuando se quiera anadir una columna
    // o fila a la sopa.
    private List<Character> letras;
    private Rd random;

    public List<String> getPalabrasValidas() {
        return palabrasValidas;
    }

    public List<CircularList<Cell>> getTablero() {
        return tablero;
    }

    public boolean marcada(String palabra) {
        return palabrasMarcadas.indexOf(palabra) != -1;
    }

    public boolean valida(String palabra) {
        return palabrasValidas.indexOf(palabra) != -1;
    }

    public void remove(String palabra) {
        palabrasValidas.remove(palabra);
        palabrasMarcadas.pushBack(palabra);
    }

    public String mark(Cell from, Cell to, Direction direction) {
        // TODO: Change this for a Logger
        System.out.println(direction);
        System.out.printf("Cell1(%d, %d), Cell2(%d, %d)%n", from.getRow(), from.getCol(), to.getRow(), to.getCol());
        switch (direction) {
        case ROW:  return markRow(from, to);
        case COL:  return markCol(from, to);
        case DIAG: return markDiag(from, to);
        }

        // Unreachable
        throw new RuntimeException("mark: palabra was null");
    }

    String markRow(Cell from, Cell to) {
        List<Cell> row = tablero.get(from.getRow());
        StringBuilder sb = new StringBuilder();
        List<Cell> toMark = new Vector<>();

        Minmax<Integer> m = new Minmax<>(from.getCol(), to.getCol());
        for (int i = m.getMin(); i < row.size() && i <= m.getMax(); i++) {
            Cell cell = row.get(i);
            sb.append(cell.getLetter());
            toMark.pushBack(cell);
        }

        return checkAndMark(sb.toString(), toMark);
    }

    String markCol(Cell from, Cell to) {
        int col = from.getCol();
        StringBuilder sb = new StringBuilder();
        List<Cell> toMark = new Vector<>();

        Minmax<Integer> m = new Minmax<>(from.getRow(), to.getRow());
        for (int i = m.getMin(); i < tablero.size() && i <= m.getMax(); i++) {
            Cell cell = tablero.get(i).get(col);
            sb.append(cell.getLetter());
            toMark.pushBack(cell);
        }

        return checkAndMark(sb.toString(), toMark);
    }

    String markDiag(Cell from, Cell to) {
        StringBuilder sb = new StringBuilder();
        List<Cell> toMark = new Vector<>();

        int i = from.getRow();
        int j = from.getCol();
        int maxX = to.getRow() + (i < to.getRow() ? 1 : -1);
        int maxY = to.getCol() + (j < to.getCol() ? 1 : -1);

        while (i != maxX && j != maxY) {
            Cell cell = tablero.get(i).get(j);
            sb.append(cell.getLetter());
            toMark.pushBack(cell);

            i += from.getRow() < to.getRow() ? 1 : -1;
            j += from.getCol() < to.getCol() ? 1 : -1;
        }

        return checkAndMark(sb.toString(), toMark);
    }

    String checkAndMark(String palabra, List<Cell> cells) {
        if (palabrasValidas.indexOf(palabra) != -1) {
            cells.forEach(cell -> cell.setMarked(true));
            return palabra;
        }
        if (palabrasValidas.indexOf(reverse(palabra)) != -1) {
            cells.forEach(cell -> cell.setMarked(true));
            return reverse(palabra);
        }
        return palabra;
    }

    String reverse(String orig) {
        StringBuilder sb = new StringBuilder(orig);
        sb.reverse();
        return sb.toString();
    }

    // TODO: change this parameter to an enum
    public void rotarFila(String direccion, int row) {
        switch (direccion) {
            case "left":
                tablero.get(row).shiftLeft();
                break;
            case "right":
                tablero.get(row).shiftRight();
                break;
            default:
                throw new RuntimeException("Invalid rotation direction: " + direccion);
        }

        // We need to reassign cell coordinates after shifting
        for (int i = 0; i < tablero.get(row).size(); i++) {
            Cell cell = tablero.get(row).get(i);
            cell.setRow(row);
            cell.setCol(i);
        }
    }

    public void anadirColumna() {
        for (int row = 0; row < rows; row++)
            tablero.get(row).pushBack(new Cell(random.choice(letras), row, cols));
        cols++;
    }

    public void anadirFila() {
        CircularList<Cell> row = new CircularList<>();
        for (int col = 0; col < cols; col++) {
            Cell cell = new Cell(random.choice(letras), rows, col);
            row.pushBack(cell);
        }
        rows++;
        tablero.pushBack(row);
    }

    public void eliminarColumna(int col) {
        for (int i = 0; i < rows; i++) {
            Cell cell = tablero.get(i).get(col);
            tablero.get(i).remove(cell);
        }
    }

    public void eliminarFila(int row) {
        tablero.remove(tablero.get(row));
    }

    public SopaLetras(int rows, int cols, String tema) throws Exception {
        palabrasValidas  = new Vector<>();
        palabrasMarcadas = new Vector<>();
        this.rows = rows;
        this.cols = cols;
        random = new Rd();

        List<String> palabras = FS.readFile(getClass().getResource("/data/" + tema + ".txt").toURI());
        for (int i = 0; i < 10;) {
            String p = random.choice(palabras);
            if (palabrasValidas.indexOf(p) == -1) {
                palabrasValidas.pushBack(p);
                i++;
            }
        }

        // De esta lista vamos a seleccionar las letras para la sopa
        letras = new Vector<>();
        for (String palabra : palabrasValidas) {
            for (char c : palabra.toCharArray()) {
                // TODO: Quiza deberiamos chequear que no este metida ya,
                // para darle a todas las letras la misma oportunidad de ser
                // escogidas
                letras.pushBack(c);
            }
        }

        // LLenamos el tablero
        tablero = new Vector<>();
        for (int i = 0; i < rows; i++) {
            CircularList<Cell> row = new CircularList<>();
            for (int j = 0; j < cols; j++) {
                Cell cell = new Cell(random.choice(letras), i, j);
                row.pushBack(cell);
            }
            tablero.pushBack(row);
        }
    }

    public SopaLetras(int rows, int cols) throws Exception {
        this(rows, cols, "animales");
    }

    public SopaLetras() throws Exception {
        this(12, 12);
    }
}
