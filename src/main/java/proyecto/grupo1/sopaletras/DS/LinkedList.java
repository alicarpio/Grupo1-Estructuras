package proyecto.grupo1.sopaletras.DS;

public class LinkedList<T> implements List<T> {
    private int size;
    private Node head;
    private Node tail;

    private class Node {
        T data;
        Node next;
        Node prev;
        public Node(T data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

        public Node(T data) {
            this(data, null, null);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int indexOf(T elem) {
        int index = 0;

        for (T data : this) {
            if (elem.equals(data)) return index;
            index++;
        }

        return -1;
    }

    @Override
    public List<T> pushFront(T elem) {
        Node tmp = new Node(elem);

        if (head == null) {
            head = tail = tmp;
        }
        else {
            Node oldHead = head;
            head = tmp;
            oldHead.prev = head;
            head.next = oldHead;
        }

        size++;
        return this;
    }

    @Override
    public List<T> pushBack(T elem) {
        if (head == null) return pushFront(elem);

        if (tail == null) {
            tail = new Node(elem, null, head);
        }
        else {
            Node newTail = new Node(elem, null, tail);
            tail.next = newTail;
            tail = newTail;
        }

        size++;
        return this;
    }

    @Override
    public T popFront() {
        if (head == null) return null;

        T elem = head.data;
        head = head.next;
        head.prev = null;
        size--;
        return elem;
    }

    @Override
    public T popBack() {
        if (head == null) return null;
        if (size == 1) return popFront();
        T data = tail.data;
        tail.prev.next = null;
        return data;
    }

    @Override
    public T get(int index) {
        int i = 0;
        for (Node curr = head; curr != null; curr = curr.next) {
            T data = curr.data;
            if (i == index) {
                return data;
            }
            i++;
        }
        return null;
    }

    @Override
    public List<T> remove(T elem) {
        if (size == 1 && head.data.equals(elem)) {
            head = null;
            return this;
        }

        Node curr;
        for (curr = head; curr != null && !curr.data.equals(elem); curr = curr.next);

        if (curr == null) return this;

        if (curr.next != null) {
            curr.prev.next = curr.next;
            curr.next.prev = curr.prev;
        } else {
            curr.prev.next = null;
        }

        size--;
        return this;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for (T data : this) { s.append(data + ", "); }
        s.setLength(s.length() - 2);
        s.append("]");
        return s.toString();
    }
}
