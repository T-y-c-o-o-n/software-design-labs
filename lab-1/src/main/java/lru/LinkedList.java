package lru;

public class LinkedList<T, R> {
    private final Node<T, R> firstDummy;
    private final Node<T, R> lastDummy;

    LinkedList() {
        firstDummy = new Node<>(null, null);
        lastDummy = new Node<>(null, null);
        firstDummy.next = lastDummy;
        lastDummy.prev = firstDummy;
    }

    Node<T, R> first() {
        Node<T, R> firstDummyNext = firstDummy.next;
        assert firstDummyNext != lastDummy;
        return firstDummyNext;
    }

    Node<T, R> last() {
        Node<T, R> lastDummyPrev = lastDummy.prev;
        assert firstDummy != lastDummyPrev;
        return lastDummyPrev;
    }

    void cut(Node<T, R> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.next = null;
        node.prev = null;
    }

    void putFirst(Node<T, R> node) {
        Node<T, R> next = firstDummy.next;
        next.prev = node;
        node.next = next;
        firstDummy.next = node;
        node.prev = firstDummy;
    }

    boolean isEmpty() {
        return firstDummy.next == lastDummy;
    }

    // Just for debugging
    void printList() {
        Node<T, R> node = firstDummy;
        System.out.print("List:");
        while (node != null) {
            System.out.printf(" - %s", node.value);
            node = node.next;
        }
        System.out.println();
    }

    static class Node<T, R> {
        private final T key;
        private R value;  // null means first or last
        private Node<T, R> prev;
        private Node<T, R> next;

        Node(T key, R value) {
            this.key = key;
            this.value = value;
        }

        T getKey() {
            return key;
        }

        R getValue() {
            return value;
        }

        void setValue(R value) {
            this.value = value;
        }
    }
}
