import org.junit.Assert;

import java.sql.Array;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListTest {

    @org.junit.jupiter.api.Test
    void add() {
        ArrayList<Integer> expected = new ArrayList<>(new Integer[] {1, 2, 3});
        ArrayList<Integer> actual = new ArrayList<>(new Integer[] {1, 2});
        actual.add(3);

        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testAddByIndex() {
        ArrayList<Integer> expected = new ArrayList<>(new Integer[]{1, 2, 4, 3});
        ArrayList<Integer> actual = new ArrayList<>(new Integer[]{1, 2, 3});
        actual.add(2, 4);

        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void get() {
        Integer expected = 4;
        ArrayList<Integer> testArray = new ArrayList<>(new Integer[]{1, 2, 4});
        Integer actual = testArray.get(2);

        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void remove() {
        ArrayList<Integer> expected = new ArrayList<>(new Integer[]{1, 2, 3, 4, 5});
        ArrayList<Integer> actual = new ArrayList<>(new Integer[]{1, 2, 3, 4, 6, 5});
        actual.remove(4);
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testRemoveByElement() {
        ArrayList<Integer> expected = new ArrayList<>(new Integer[] {1, 2, 3, 4, 5});
        ArrayList<Integer> actual = new ArrayList<>(new Integer[] {1, 2, 3, 4, 5});

        Integer toRemove = 7;
        actual.remove(toRemove);
        assertEquals(expected, actual);

        actual.add(7);
        actual.remove(toRemove);
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void clear() {
        ArrayList<Integer> expected = new ArrayList<>();
        ArrayList<Integer> actual = new ArrayList<>(new Integer[]{1, 2, 3});
        actual.clear();

        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void contains() {
        Integer toCheck = 1;
        ArrayList<Integer> arrayList = new ArrayList<>(new Integer[]{1, 2, 3});
        assertTrue(arrayList.contains(toCheck));

        arrayList.remove(toCheck);
        assertFalse(arrayList.contains(toCheck));
    }

    @org.junit.jupiter.api.Test
    void defaultSort() {
        ArrayList<Integer> expected = new ArrayList<>(new Integer[]{1, 2, 3, 4, 5});
        ArrayList<Integer> actual = new ArrayList<>(new Integer[]{2, 3, 1, 8, 5});
        actual.sort();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void sort() {
        ArrayList<Integer> expected = new ArrayList<>(new Integer[]{5, 4, 3, 2, 1});
        ArrayList<Integer> actual = new ArrayList<>(new Integer[]{2, 3, 1, 5, 4});
        actual.sort((o1, o2) -> -o1.compareTo(o2));
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void size() {
        ArrayList<Integer> checker = new ArrayList<>(new Integer[]{1, 2, 3});
        Integer expected = 3;
        Integer actual = checker.size();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void indexOf() {
        ArrayList<Integer> checker = new ArrayList<>(new Integer[]{6, 10, 30, 255, 6});
        Integer toCheck = 6;
        Integer expected = 0;
        Integer actual = checker.indexOf(toCheck);
        assertEquals(expected, actual);

        toCheck = 100;
        expected = -1;
        actual = checker.indexOf(toCheck);
        assertEquals(expected, actual);
    }
}