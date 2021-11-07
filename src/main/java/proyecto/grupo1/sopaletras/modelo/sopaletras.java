package proyecto.grupo1.sopaletras.modelo;

import proyecto.grupo1.sopaletras.DS.List;
import proyecto.grupo1.sopaletras.DS.Vector;
import proyecto.grupo1.sopaletras.DS.CircularList;

public class SopaLetras {
    private List<CircularList<Cell>> tablero;
    private List<String> palabrasValidas;

    public SopaLetras(int N, String tema) {
        palabrasValidas = new Vector<>();
        // TODO
        //palabrasValidas = FS.readFile(getClass().getResource("/data/" + tema + ".txt"));
        tablero = new Vector<>();

        // LLenamos el tablero
        List<Character> letras = new Vector<>();
        for (String palabra : palabrasValidas) {
            for (char c : palabra.toCharArray()) {
                letras.pushBack(c);
            }
        }

        for (int i = 0; i < N; i++) {
            CircularList<Cell> row = new CircularList<>();
            for (int j = 0; j < N; j++) {
                // TODO: seleccionar letra random de letras
            }
            tablero.pushBack(row);
        }
    }

    public SopaLetras(int N) {
        this(N, "animales");
    }

    public SopaLetras() {
        this(6);
    }
}
