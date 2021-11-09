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

    public SopaLetras(int N, String tema) throws Exception {
        palabrasValidas = FS.readFile(getClass().getResource("/data/" + tema + ".txt").toURI());
        // TODO: hacer esto menos retrasado
        // Estamos limitando el numero de palabras a 10
        while (palabrasValidas.size() > 10)
            palabrasValidas.popBack();

        // De esta lista vamos a seleccionar las letras para la sopa
        letras = new Vector<>();
        for (String palabra : palabrasValidas) {
            for (char c : palabra.toCharArray()) {
                if (!Character.isWhitespace(c))
                    letras.pushBack(c);
            }
        }

        // LLenamos el tablero
        tablero = new Vector<>();
        random = new Rd();
        for (int i = 0; i < N; i++) {
            CircularList<Cell> row = new CircularList<>();
            for (int j = 0; j < N; j++) {
                Cell cell = new Cell(random.choice(letras), i, j);
                row.pushFront(cell);
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
