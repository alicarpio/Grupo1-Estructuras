package proyecto.grupo1.sopaletras.modelo;

import java.util.concurrent.ThreadLocalRandom;
import java.lang.IndexOutOfBoundsException;

import proyecto.grupo1.sopaletras.DS.List;

public class Rd {
    private ThreadLocalRandom random;

    public Rd() {
        random = ThreadLocalRandom.current();
    }

    public <T> T choice(List<T> source) {
        if (source.isEmpty())
            throw new IndexOutOfBoundsException("Cannot choose from an empty sequence");
        int len = source.size();
        int index = random.nextInt(len);
        return source.get(index);
    }
}
