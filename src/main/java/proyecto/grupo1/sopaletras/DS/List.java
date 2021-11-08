package proyecto.grupo1.sopaletras.DS;

import java.util.Iterator;

public interface List<T> extends Iterable<T> {
    int size();
    int indexOf(T elem);
    List<T> pushFront(T elem);
    List<T> pushBack(T elem);
    T popFront();
    T popBack();
    T get(int index);
    List<T> remove(T elem);
    boolean isEmpty();
}
