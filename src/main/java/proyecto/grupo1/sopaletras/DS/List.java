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

    // Note: This implementation means List implementations can't use a
    // foreach loop when implementing get or size
    @Override
    default Iterator<T> iterator() {
        return new Iterator<T>(){
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < size();
            }

            @Override
            public T next() {
                T elem = get(i);
                i++;
                return elem;
            }
        };
    }
}
