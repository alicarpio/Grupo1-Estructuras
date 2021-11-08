package proyecto.grupo1.sopaletras.modelo;

import java.io.IOException;

import proyecto.grupo1.sopaletras.DS.List;
import proyecto.grupo1.sopaletras.DS.Vector;
import proyecto.grupo1.sopaletras.DS.CircularList;

public class SopaLetras {
    private List<CircularList<Cell>> tablero;
    private List<String> palabrasValidas;

    public List<String> getPalabrasValidas() {
        return palabrasValidas;
    }

    public SopaLetras(int N, String tema) throws Exception {
        palabrasValidas = FS.readFile(getClass().getResource("/data/" + tema + ".txt").toURI());
        // TODO: hacer esto menos retrasado
        while (palabrasValidas.size() > 10)
            palabrasValidas.popBack();

        // De esta lista vamos a seleccionar las letras para la sopa
        List<Character> letras = new Vector<>();
        for (String palabra : palabrasValidas) {
            for (char c : palabra.toCharArray()) {
                letras.pushBack(c);
            }
        }

        // LLenamos el tablero
        tablero = new Vector<>();
        Rd random = new Rd();
        for (int i = 0; i < N; i++) {
            CircularList<Cell> row = new CircularList<>();
            for (int j = 0; j < N; j++) {
                Cell cell = new Cell(random.choice(letras));
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
