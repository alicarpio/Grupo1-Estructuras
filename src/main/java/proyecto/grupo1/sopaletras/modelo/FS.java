package proyecto.grupo1.sopaletras.modelo;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.IOException;
import java.net.URI;

import proyecto.grupo1.sopaletras.DS.Vector;
import proyecto.grupo1.sopaletras.DS.List;

public class FS {
    public static List<String> readFile(URI path) throws IOException {
        List<String> contents = new Vector<>();
        // TODO: change this for buffered reader
        String[] lines = Files.readString(Paths.get(path), Charset.forName("Cp1252")).split("\\n");
        for (String line : lines) {
            contents.pushBack(line);
        }
        return contents;
    }
}
