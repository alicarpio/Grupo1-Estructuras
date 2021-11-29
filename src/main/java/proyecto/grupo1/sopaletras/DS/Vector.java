package proyecto.grupo1.sopaletras.DS;

import java.util.Arrays; // TODO: revisar
import java.util.Iterator;

public class Vector<T> implements List<T> {
    private T[] buffer;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public Vector(int N) {
        buffer = (T[]) new Object[N];
        size = 0;
        capacity = N;
    }

    public Vector() {
        this(10);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int indexOf(T elem) {
        int idx;
        for (idx = 0; idx < size && !buffer[idx].equals(elem); idx++);
        return idx == size ? -1 : idx;
    }

    private void shiftLeft(int index) {
        for (int i = index; i < size - 1; i++) {
            buffer[i] = buffer[i+1];
        }
    }

    @Override
    public List<T> remove(T elem) {
        shiftLeft(indexOf(elem));
        buffer[--size] = null;
        return this;
    }

    @SuppressWarnings("unchecked")
    private void enlarge(int newCapacity) {
        T[] newBuffer = (T[])new Object[newCapacity];
        System.arraycopy(buffer, 0, newBuffer, 0, size);
        capacity = newCapacity;
        buffer = newBuffer;
    }

    private boolean isFull() { return size >= capacity; }

    @Override
    public List<T> pushBack(T elem) {
        if (isFull()) enlarge(capacity * 2);
        buffer[size++] = elem;
        return this;
    }

    @Override
    public List<T> replace(T elem, int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static class VectorIndexOutOfBoundsException
            extends RuntimeException {
        public VectorIndexOutOfBoundsException(String msg) {
            super(msg);
        }
    }

    @Override public T get(int index) {
        if (index >= size) {
            throw new VectorIndexOutOfBoundsException(
                    "Index " + index +
                    "out of bounds for vector with size " +
                    size);
        }
        return buffer[index];
    }

    private void shiftRight(int index) {
        for (int i = size; i > index; i--) {
            buffer[i] = buffer[i-1];
        }
    }

    @Override
    public List<T> pushFront(T elem) {
        if (isFull()) enlarge(capacity * 2);
        shiftRight(0);
        buffer[0] = elem;
        size++;
        return this;
    }

    @Override
    public T popFront() {
        T elem = buffer[0];
        shiftLeft(0);
        size--;
        return elem;
    }

    @Override
    public T popBack() {
        T elem = buffer[--size];
        buffer[size] = null;
        return elem;
    }

    public void extend(List<T> other) {
        for (T elem : other)
            pushBack(elem);
    }

    @Override
    public Iterator<T> iterator() {
        // for (int i = 0; i < size; i++)
        return new Iterator<>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < size;
            }

            @Override
            public T next() {
                return buffer[i++];
            }
        };
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(buffer);
        result = prime * result + capacity;
        result = prime * result + size;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector other = (Vector) obj;
        if (!Arrays.deepEquals(buffer, other.buffer))
            return false;
        if (capacity != other.capacity)
            return false;
        if (size != other.size)
            return false;
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder();

        str.append("[");
        for (int idx = 0; idx < size; ++idx) {
            if (idx != (size - 1)) {
                str.append(buffer[idx]).append(", ");
            } else {
                str.append(buffer[idx]);
            }
        }
        str.append("]");

        return str.toString();
    }
}
