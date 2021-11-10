package proyecto.grupo1.sopaletras.modelo;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.Normalizer;
import java.util.regex.Pattern;

import proyecto.grupo1.sopaletras.DS.Vector;
import proyecto.grupo1.sopaletras.DS.List;

public class FS {
    public static List<String> readFile(URI path) throws IOException {
        // TODO: quitarle las tildes a los archivos
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {
            Pattern pattern = Pattern.compile("[^a-zA-Z]");
            return reader.lines()
                .map(s -> Normalizer.normalize(s, Normalizer.Form.NFD))
                .map(s -> pattern.matcher(s).replaceAll(""))
                .collect(Vector::new,Vector::pushBack,Vector::extend);
        }
    }
}
