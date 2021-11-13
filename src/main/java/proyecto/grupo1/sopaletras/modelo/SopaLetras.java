package proyecto.grupo1.sopaletras.modelo;

import java.io.IOException;

import proyecto.grupo1.sopaletras.DS.List;
import proyecto.grupo1.sopaletras.DS.Vector;
import proyecto.grupo1.sopaletras.DS.CircularList;

public class SopaLetras {
    private List<CircularList<Cell>> tablero;
    private List<String> palabrasValidas;
    private List<String> palabrasMarcadas;

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

        return null; // Unreachable
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

        Minmax<Integer> rowM = new Minmax<>(from.getRow(), to.getRow());
        Minmax<Integer> colM = new Minmax<>(from.getCol(), to.getCol());

        int i = rowM.getMin();
        int j = colM.getMin();

        for (; i < rowM.getMax() && j < colM.getMax()
                && i < tablero.size() && j < tablero.get(i).size(); i++, j++) {
            Cell cell = tablero.get(i).get(j);
            sb.append(cell.getLetter());
            toMark.pushBack(cell);
        }

        return checkAndMark(sb.toString(), toMark);
    }

    String checkAndMark(String palabra, List<Cell> cells) {
        String palabraMarcada  = checkPalabra(palabra);
        if (palabraMarcada != null)
            cells.forEach(cell -> cell.setMarked(true));
        return palabraMarcada;
    }

    String reverse(String orig) {
        StringBuilder sb = new StringBuilder(orig);
        sb.reverse();
        return sb.toString();
    }

    String checkPalabra(String palabra) {
        if (palabrasValidas.indexOf(palabra) != -1)          return palabra;
        if (palabrasValidas.indexOf(reverse(palabra)) != -1) return reverse(palabra);
        return null;
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
        // TODO: aqui solo es necesario reasignar los indices de la columna of
        // fila marcada.
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
        palabrasValidas  = new Vector<>();
        palabrasMarcadas = new Vector<>();
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
