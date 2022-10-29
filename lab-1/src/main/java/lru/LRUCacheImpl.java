package lru;

import java.util.HashMap;
import java.util.Objects;

import static lru.LinkedList.Node;

public class LRUCacheImpl<T, R> implements LRUCache<T, R> {

    private final int maxSize;
    private final HashMap<T, Node<T, R>> keyToNode;
    private final LinkedList<T, R> linkedList;

    public LRUCacheImpl(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize must be > 0, but was " + maxSize);
        }
        this.maxSize = maxSize;
        keyToNode = new HashMap<>();
        linkedList = new LinkedList<>();
    }

    @Override
    public R get(T key) {
        Node<T, R> node = keyToNode.get(key);
        if (node != null) {
            linkedList.cut(node);
            linkedList.putFirst(node);

            assert node.equals(linkedList.first());

            return node.getValue();
        }
        return null;
    }

    @Override
    public void put(T key, R value) {
        assert value != null;

        Node<T, R> node = keyToNode.get(key);
        if (node != null) {
            assert Objects.equals(node.getKey(), key);
            node.setValue(value);

            if (linkedList.first() != node) {
                linkedList.cut(node);
                linkedList.putFirst(node);

                assert node.equals(linkedList.first());
            }

            return;
        } else {
            if (keyToNode.size() == maxSize) {
                Node<T, R> last = linkedList.last();
                linkedList.cut(last);

                assert linkedList.isEmpty() || !linkedList.last().equals(last);

                keyToNode.remove(last.getKey());
            }
            node = new Node<>(key, value);
            keyToNode.put(key, node);
        }

        linkedList.putFirst(node);
        assert node.equals(linkedList.first());
    }
}
