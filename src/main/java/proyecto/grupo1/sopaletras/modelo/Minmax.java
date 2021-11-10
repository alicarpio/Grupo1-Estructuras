package proyecto.grupo1.sopaletras.modelo;

public class Minmax<T> {
    private T min;
    private T max;

    public Minmax(T fst, T snd) {
        min = ((Comparable<T>)fst).compareTo(snd) < 0 ? fst : snd;
        max = ((Comparable<T>)fst).compareTo(snd) >= 0 ? fst : snd;
    }

    public T getMax() {
        return max;
    }

    public T getMin() {
        return min;
    }
}
