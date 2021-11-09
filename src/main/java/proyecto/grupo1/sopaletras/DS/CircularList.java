package proyecto.grupo1.sopaletras.DS;

import java.util.Iterator;

public class CircularList<T> implements List<T> {
    private int size;
    private Node last;

    private class Node {
        private T data;
        private Node next;

        public Node(T data) {
            this.data = data;
        }
    }

    private Node head() { return last.next; }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int indexOf(T elem) {
        if (last.data.equals(elem)) return size-1;

        int i = 0;
        Node curr = head();
        while (curr != last) {
            assert curr != null : "indexOf: Node in circular list is null: i=" + i;
            if (curr.data.equals(elem)) return i;
            curr = curr.next;
            i++;
        }
        return -1;
    }

    @Override
    public List<T> pushFront(T elem) {
        Node newHead = new Node(elem);
        if (last == null) {
            last = newHead;
            last.next = last;
        }
        else {
            newHead.next = head();
            last.next = newHead;
        }
        size++;
        return this;
    }

    @Override
    public List<T> pushBack(T elem) {
        if (last == null) return pushFront(elem);

        Node newTail = new Node(elem);
        newTail.next = head();
        last.next = newTail;
        last = newTail;

        size++;
        return this;
    }

    @Override
    public T popFront() {
        if (isEmpty()) return null;

        T elem = head().data;
        last.next = head().next;

        if (isEmpty()) last = null;

        size--;
        return elem;
    }

    @Override
    public T popBack() {
        if (isEmpty()) return null;

        Node newTail = head();

        while (newTail.next != last) newTail = newTail.next;

        newTail.next = head();
        T elem = last.data;
        last = newTail;
        size--;

        if (isEmpty()) last = null;

        return elem;
    }

    @Override
    public T get(int index) {
        if (index >= size)
            throw new IllegalArgumentException("Invalid index: " +
                    index + " for list of size: " + size);

        int i = 0;
        Node curr = head();
        while (i < size) {
            if (index == i) return curr.data;
            curr = curr.next;
            i++;
        }

        return null;
    }

    @Override
    public List<T> remove(T elem) {
        if (isEmpty()) return this;

        int i = 0;
        Node curr = head();
        while (i < size && !curr.next.data.equals(elem)) {
            curr = curr.next; i++;
        }

        assert curr.next.data.equals(elem) || size == i : "remove: Removing wrong element!";

        if (i < size) {
            // Update the last pointer if we are removing the last element
            if (last == curr.next) last = curr;
            curr.next = curr.next.next;
            size--;
        }

        return this;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            Node current = head();
            boolean startedIteration = false;

            @Override
            public boolean hasNext() {
                if (!startedIteration && !isEmpty()) {
                    startedIteration = true;
                    return true;
                }
                else {
                    return current != head();
                }
            }

            @Override
            public T next() {
                T elem = current.data;
                current = current.next;
                return elem;
            }
        };
    }

    // "Shift" elements one place to the left
    public void shiftLeft() { last = last.next; }

    public void shiftRight() {
        Node current = head();
        while (current.next != last)
            current = current.next;
        last = current;
    }
}
