package lru;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LRUCacheImplTest {

    @Test(expected = IllegalArgumentException.class)
    public void illegalMaxSize() {
        new LRUCacheImpl<>(-1);
    }

    @Test
    public void oneElementTest() {
        LRUCache<Integer, Integer> lru = new LRUCacheImpl<>(1);
        int key = 3;
        int value = -600;
        lru.put(key, value);

        int gotValue = lru.get(key);
        assertEquals(value, gotValue);
    }

    @Test
    public void severalElementsInCacheTest() {
        severalElementsInCacheScenario(2);
        severalElementsInCacheScenario(3);
        severalElementsInCacheScenario(5);
        severalElementsInCacheScenario(10);
        severalElementsInCacheScenario(100);
    }

    public static void severalElementsInCacheScenario(int maxSize) {
        LRUCache<Integer, Integer> lru = new LRUCacheImpl<>(maxSize);
        for (int i = 0; i < maxSize; i++) {
            lru.put(i, i * i);
        }

        for (int i = 0; i < maxSize; i++) {
            int gotValue = lru.get(i);
            assertEquals(i * i, gotValue);
        }
    }

    @Test
    public void oneExtraElementTest() {
        oneExtraElementScenario(2);
        oneExtraElementScenario(3);
        oneExtraElementScenario(5);
        oneExtraElementScenario(10);
        oneExtraElementScenario(100);
    }

    public static void oneExtraElementScenario(int maxSize) {
        LRUCache<Integer, Integer> lru = new LRUCacheImpl<>(maxSize);

        for (int i = 0; i < maxSize; i++) {
            lru.put(i, i * i);
        }
        // put extra element
        lru.put(maxSize, maxSize * maxSize);

        assertNull(lru.get(0));
        for (int i = 0; i < maxSize; i++) {
            int gotValue = lru.get(i + 1);
            assertEquals((i + 1) * (i + 1), gotValue);
        }
    }

    @Test
    public void severalExtraElementsTest() {
        severalExtraElementsScenario(2, 3);
        severalExtraElementsScenario(3, 6);
        severalExtraElementsScenario(5, 1009);
        severalExtraElementsScenario(8, 12);
        severalExtraElementsScenario(12, 143);
        severalExtraElementsScenario(35, 34);
        severalExtraElementsScenario(101, 9);
    }

    public static void severalExtraElementsScenario(int maxSize, int extraElements) {
        LRUCache<Integer, Integer> lru = new LRUCacheImpl<>(maxSize);

        for (int i = 0; i < maxSize; i++) {
            lru.put(i, i * i);
        }
        // put extra elements
        for (int i = maxSize; i < maxSize + extraElements; i++) {
            lru.put(i, i * i);
        }

        for (int i = 0; i < extraElements; i++) {
            assertNull(lru.get(i));
        }
        for (int i = extraElements; i < maxSize + extraElements; i++) {
            int gotValue = lru.get(i);
            assertEquals(i * i, gotValue);
        }
    }

    @Test
    public void lastQueriedElementDeletedTest() {
        lastQueriedElementDeletedByGetAndPutScenario(2, 0);
        lastQueriedElementDeletedByGetAndPutScenario(2, 1);
        lastQueriedElementDeletedByGetAndPutScenario(3, 0);
        lastQueriedElementDeletedByGetAndPutScenario(3, 1);
        lastQueriedElementDeletedByGetAndPutScenario(3, 2);
        lastQueriedElementDeletedByGetAndPutScenario(5, 2);
        lastQueriedElementDeletedByGetAndPutScenario(5, 3);
        lastQueriedElementDeletedByGetAndPutScenario(9, 4);
        lastQueriedElementDeletedByGetAndPutScenario(9, 5);
        lastQueriedElementDeletedByGetAndPutScenario(9, 7);
        lastQueriedElementDeletedByGetAndPutScenario(20, 0);
        lastQueriedElementDeletedByGetAndPutScenario(20, 11);
        lastQueriedElementDeletedByGetAndPutScenario(20, 18);
        lastQueriedElementDeletedByGetAndPutScenario(50, 0);
        lastQueriedElementDeletedByGetAndPutScenario(50, 1);
        lastQueriedElementDeletedByGetAndPutScenario(50, 10);
        lastQueriedElementDeletedByGetAndPutScenario(50, 22);
        lastQueriedElementDeletedByGetAndPutScenario(50, 33);
        lastQueriedElementDeletedByGetAndPutScenario(50, 40);
        lastQueriedElementDeletedByGetAndPutScenario(50, 45);
        lastQueriedElementDeletedByGetAndPutScenario(50, 49);
    }

    public static void lastQueriedElementDeletedByGetAndPutScenario(int maxSize, int notQueriedElement) {
        lastQueriedElementDeletedScenario(maxSize, notQueriedElement, true);
        lastQueriedElementDeletedScenario(maxSize, notQueriedElement, false);
    }

    public static void lastQueriedElementDeletedScenario(int maxSize, int notQueriedElement, boolean queriedByGet) {
        if (notQueriedElement < 0 || maxSize <= notQueriedElement) {
            notQueriedElement = maxSize / 2;
        }

        LRUCache<Integer, Integer> lru = new LRUCacheImpl<>(maxSize);

        for (int i = 0; i < maxSize; i++) {
            lru.put(i, i * i);
        }

        // Querying elements by get
        for (int i = 0; i < maxSize; i++) {
            if (i != notQueriedElement) {
                if (queriedByGet) {
                    lru.get(i);
                } else {
                    lru.put(i, i * i * i);
                }
            }
        }

        // put extra element
        lru.put(maxSize, maxSize * maxSize);

        assertNull(lru.get(notQueriedElement));
    }
}
