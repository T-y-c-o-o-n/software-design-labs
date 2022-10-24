package lru;

import java.util.HashMap;

public class LRUCacheImpl<T, R> implements LRUCache<T, R> {

    private final int maxSize;
    private final HashMap<T, Node<T, R>> keyToNode;
    private final Node<T, R> firstDummy;
    private final Node<T, R> lastDummy;

    public LRUCacheImpl(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize must be > 0, but was " + maxSize);
        }
        this.maxSize = maxSize;
        keyToNode = new HashMap<>();
        firstDummy = new Node<>(null, null);
        lastDummy = new Node<>(null, null);
        firstDummy.next = lastDummy;
        lastDummy.prev = firstDummy;
    }

    @Override
    public R get(T key) {
        Node<T, R> node = keyToNode.get(key);
        if (node != null) {
            moveToFirst(node);
            return node.value;
        }
        return null;
    }

    private void cut(Node<T, R> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToFirst(Node<T, R> node) {
        cut(node);
        putAsFirst(node);
    }

    private void putAsFirst(Node<T, R> node) {
        // put as the first
        Node<T, R> next = firstDummy.next;
        next.prev = node;
        node.next = next;
        firstDummy.next = node;
        node.prev = firstDummy;
    }

    @Override
    public void put(T key, R value) {
        Node<T, R> node = keyToNode.get(key);
        if (node != null) {
            node.value = value;
            if (firstDummy.next == node) {
                // node is already the first one
                return;
            }

            moveToFirst(node);
            return;
        } else {
            if (keyToNode.size() == maxSize) {
                Node<T, R> lastNode = lastDummy.prev;
                cut(lastNode);
                keyToNode.remove(lastNode.key);
            }

            node = new Node<>(key, value);
            keyToNode.put(key, node);
        }

        putAsFirst(node);
    }

    private static class Node<T, R> {
        private T key;
        private R value;  // null means first or last
        private Node<T, R> prev;
        private Node<T, R> next;

        private Node(T key, R value) {
            this.key = key;
            this.value = value;
        }
    }

    // Just for debugging
    private void printList() {
        Node<T, R> node = firstDummy;
        System.out.print("List:");
        while (node != null) {
            System.out.printf(" - %s", node.value);
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        LRUCacheImpl<Integer, Integer> lRUCache = new LRUCacheImpl<>(3);
        lRUCache.put(1, 1);
        lRUCache.printList();
        lRUCache.put(2, 2);
        lRUCache.printList();
        lRUCache.put(3, 3);
        lRUCache.printList();
        lRUCache.put(4, 4);
        lRUCache.printList();
        System.out.println(lRUCache.get(4));
        lRUCache.printList();
        System.out.println(lRUCache.get(3));
        lRUCache.printList();
        System.out.println(lRUCache.get(2));
        lRUCache.printList();
        System.out.println(lRUCache.get(1));
        lRUCache.printList();
        lRUCache.put(5, 5);
        lRUCache.printList();
        System.out.println(lRUCache.get(1));
        lRUCache.printList();
        System.out.println(lRUCache.get(2));
        lRUCache.printList();
        System.out.println(lRUCache.get(3));
        lRUCache.printList();
        System.out.println(lRUCache.get(4));
        lRUCache.printList();
        System.out.println(lRUCache.get(5));
        lRUCache.printList();
    }
}
