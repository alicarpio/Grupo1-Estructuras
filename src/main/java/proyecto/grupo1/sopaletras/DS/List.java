package proyecto.grupo1.sopaletras.DS;

public interface List<T> extends Iterable<T> {
    int size();
    int indexOf(T elem);
    List<T> pushFront(T elem);
    List<T> pushBack(T elem);
//    List<T> pushIn(T elem, int index);
    List<T> replace(T elem, int index);
    T popFront();
    T popBack();
    T get(int index);
    List<T> remove(T elem);
    boolean isEmpty();
}
