package proyecto.grupo1.sopaletras.modelo;

import proyecto.grupo1.sopaletras.DS.CircularList;
import proyecto.grupo1.sopaletras.DS.List;
import proyecto.grupo1.sopaletras.DS.Vector;

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

    public List<String> getPalabrasMarcadas() {
        return palabrasMarcadas;
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
    
    public void rotarCol(String direccion, int col) {
        
        CircularList<Cell> column = new CircularList();
        
        for (int i = 0 ; i < rows ; i++) {
            column.pushBack(tablero.get(i).get(col));
        } 
        
        switch (direccion) {
            case "up":
                column.shiftLeft();
                for (int i = 0 ; i < rows ; i++) {
                    tablero.get(i).replace(column.get(i), col);
                }
                break;
            case "down":
                column.shiftRight();
                for (int i = 0 ; i < rows ; i++) {
                    tablero.get(i).replace(column.get(i), col);  
                }
                break;
            default:
                throw new RuntimeException("Invalid rotation direction: " + direccion);
        }
        
        for (int j = 0; j < tablero.size(); j++) {           
            Cell cell = tablero.get(j).get(col);
            cell.setRow(j);
            cell.setCol(col);            
        }
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
        cols--;
    }

    public void eliminarFila(int row) {
        tablero.remove(tablero.get(row));
        rows--;
    }

    public SopaLetras(int rows, int cols, String tema, String idioma) throws Exception {
        palabrasValidas  = new Vector<>();
        palabrasMarcadas = new Vector<>();
        this.rows = rows;
        this.cols = cols;
        random = new Rd();

        String path = String.format("/data/%s/%s.txt",
                idioma.equals("Español") ? "espanol/" : "ingles/", tema);
        List<String> palabras = FS.readFile(getClass().getResource(path).toURI());

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
                if (letras.indexOf(c) == -1)
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

        for (int i = 0; i < 10; i++) {
            String palabra = null;
            do {
                palabra = random.choice(palabrasValidas);
            } while (palabra.length() > rows && palabra.length() > cols);

            int usedRows = 0;
            int usedCols = 0;

            if (i % 2 == 0) {
                int nRow;
                do {
                    nRow = random.range(rows);
                } while ((usedRows & nRow) != 0);
                usedRows |= (1 << nRow);
                CircularList<Cell> row = tablero.get(nRow);
                int n = palabra.length();
                for (int j = random.range(cols - n), k = 0; k < n; j++, k++)
                    row.get(j).setLetter(palabra.charAt(k));
            }
            else {
                int nCol;
                do {
                    nCol = random.range(cols);
                } while ((usedCols & nCol) != 0);
                usedCols |= (1 << nCol);
                int n = palabra.length();
                for (int j = random.range(rows - n), k = 0; k < n; j++, k++) {
                    Cell cell = tablero.get(j).get(nCol);
                    cell.setLetter(palabra.charAt(k));
                }
            }
        }
    }

    public SopaLetras(int rows, int cols) throws Exception {
        this(rows, cols, "animales","espanol");
    }

    public SopaLetras() throws Exception {
        this(12, 12);
    }
}
