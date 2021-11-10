package proyecto.grupo1.sopaletras.modelo;

import java.io.IOException;

import proyecto.grupo1.sopaletras.DS.List;
import proyecto.grupo1.sopaletras.DS.Vector;
import proyecto.grupo1.sopaletras.DS.CircularList;

public class SopaLetras {
    private List<CircularList<Cell>> tablero;
    private List<String> palabrasValidas;

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

    public boolean tryMark(Cell from, Cell to, Direction direction) {
        // TODO: Change this for a Logger
        System.out.println(direction);
        System.out.printf("Cell1(%d, %d), Cell2(%d, %d)%n", from.getRow(), from.getCol(), to.getRow(), to.getCol());
        switch (direction) {
        case ROW: return tryMarkRow(from, to);
        case COL: return tryMarkCol(from, to);
        case DIAG: return tryMarkDiag(from, to);
        default: return false;
        }
    }

    boolean tryMarkRow(Cell from, Cell to) {
        List<Cell> row = tablero.get(from.getRow());
        StringBuilder sb = new StringBuilder();
        List<Cell> toMark = new Vector<>();

        int start = Math.min(from.getCol(), to.getCol());
        int end   = Math.max(from.getCol(), to.getCol());

        for (int i = start; i < row.size() && i <= end; i++) {
            Cell cell = row.get(i);
            sb.append(cell.getLetter());
            toMark.pushBack(cell);
        }

        return checkAndMark(sb.toString(), toMark);
    }

    boolean tryMarkCol(Cell from, Cell to) {
        int col = from.getCol();
        StringBuilder sb = new StringBuilder();
        List<Cell> toMark = new Vector<>();

        // TODO: Minmax
        int start = Math.min(from.getRow(), to.getRow());
        int end   = Math.max(from.getRow(), to.getRow());

        for (int i = start; i < tablero.size() && i <= end; i++) {
            Cell cell = tablero.get(i).get(col);
            sb.append(cell.getLetter());
            toMark.pushBack(cell);
        }

        return checkAndMark(sb.toString(), toMark);
    }

    boolean tryMarkDiag(Cell from, Cell to) {
        // TODO
        return false;
    }

    boolean checkAndMark(String palabra, List<Cell> cells) {
        if (checkPalabra(palabra)) {
            cells.forEach(cell -> cell.setMarked(true));
            return true;
        }
        return false;
    }

    String reverse(String orig) {
        StringBuilder sb = new StringBuilder(orig);
        sb.reverse();
        return sb.toString();
    }

    boolean checkPalabra(String palabra) {
        boolean found = !(palabrasValidas.indexOf(palabra) == -1
            && palabrasValidas.indexOf(reverse(palabra)) == -1);
        if (!found) {
            return false;
        }
        palabrasValidas.remove(palabra);
        return true;
    }

    // TODO: change this parameter to an enum
    public void rotarFila(String direccion, int row) {
        switch (direccion) {
            case "left":
                tablero.get(row-1).shiftLeft();
                break;
            case "right":
                tablero.get(row-1).shiftRight();
                break;
            default:
                throw new RuntimeException("Invalid rotation direction: " + direccion);
        }

        // We need to reassign cell coordinates after shifting
        for (int i = 0; i < tablero.size(); i++) {
            for (int j = 0; j < tablero.get(i).size(); j++) {
                Cell cell = tablero.get(i).get(j);
                cell.setRow(i);
                cell.setCol(j);
            }
        }
    }

    public SopaLetras(int N, String tema) throws Exception {
        List<String> palabras = FS.readFile(getClass().getResource("/data/" + tema + ".txt").toURI());
        palabrasValidas = new Vector<>();
        random = new Rd();
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
        for (int i = 0; i < N; i++) {
            CircularList<Cell> row = new CircularList<>();
            for (int j = 0; j < N; j++) {
                Cell cell = new Cell(random.choice(letras), i, j);
                row.pushBack(cell);
            }
            tablero.pushBack(row);
        }
    }

    public SopaLetras(int N) throws Exception {
        this(N, "animales");
    }

    public SopaLetras() throws Exception {
        this(6);
    }
}
