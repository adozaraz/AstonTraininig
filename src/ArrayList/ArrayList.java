package ArrayList;

import java.util.Comparator;

/**
 * ArrayList.ArrayList is an implementation of resizable array. Implements some of the list operations and permits all elements.
 * All ArrayLists have internal capacity, which tracks if new memory should be allocated.
 * Be warned, that this ArrayList.ArrayList is not Synchronized.
 */
@SuppressWarnings("unchecked")
public class ArrayList<T> implements List<T> {
    private Object[] array;
    private int freeMemory;

    private int length;

    private int lastFreeIndex;

    /**
     * Constructs an empty list with an initial capacity of ten
     */
    public ArrayList() {
        this.freeMemory = 10;
        this.array = new Object[this.freeMemory];
        this.lastFreeIndex = 0;
        this.length = 0;
    }

    /**
     * Constructs a list containing the elements of the specified array, in the order they are placed
     * @param array - The array whose elements are to be placed into this list
     */
    public ArrayList(T[] array) {
        this.freeMemory = 10;
        this.array = new Object[array.length+this.freeMemory];
        for (int i = 0; i < array.length; ++i) this.array[i] = array[i];
        this.lastFreeIndex = array.length;
        this.length = array.length;
    }

    public ArrayList(int initialCapacity) {
        this.freeMemory = initialCapacity;
        this.array = new Object[this.freeMemory];
        this.lastFreeIndex = 0;
        this.length = 0;
    }

    private void instantiateNewMemory() {
        Object[] copy = this.array;
        this.freeMemory = 15;
        this.array = new Object[this.array.length + this.freeMemory];
        for (int i = 0; i < this.array.length; ++i) this.array[i] = copy[i];
    }

    /**
     * Appends the specified element to the end of this list
     * @param element - element to be appended to this list
     */
    @Override
    public void add(T element) {
        if (this.freeMemory == 0) {
            this.instantiateNewMemory();
        }
        this.array[this.lastFreeIndex] = element;
        ++this.lastFreeIndex;
        ++this.length;
        --this.freeMemory;
    }

    /**
     * Insert the specified element at the specified position. Shifts the element
     * @param index - place in an array, where element will be inserted
     * @param element - element, which will be added to array
     */
    @Override
    public void add(int index, T element) {
        if (index >= this.length) throw new IndexOutOfBoundsException("Index is out of bounds");
        if (this.freeMemory == 0) this.instantiateNewMemory();
        for (int i = this.length; i > index; --i) this.array[i] = this.array[i-1];
        this.array[index] = element;
        ++this.lastFreeIndex;
        ++this.length;
        --this.freeMemory;
    }

    /**
     * Adds the elements specified at the list, which implements interface ArrayList.List<>, at the end of this list
     * @param list - list, which elements should be added to the end of this list
     */
    @Override
    public void add(List<T> list) {
        for (int i = 0; i < list.size(); ++i) {
            this.add(list.get(i));
        }
    }

    /**
     * Returns the element at the specified position in this list
     * @param index - index of the element to return
     * @return The element at the specified position in the list
     */
    @Override
    public T get(int index) {
        if (index >= this.length) throw new IndexOutOfBoundsException("Index is out of bounds");
        return (T) this.array[index];
    }

    /**
     * Override the element at the specified index with the new element
     * @param index - element, which should be replaced
     * @param element - element, which will be inserted
     */
    @Override
    public void set(int index, T element) {
        if (index >= this.length) throw new IndexOutOfBoundsException("Index is out of bounds");
        this.array[index] = element;
    }

    /**
     * Remove and return the element at the specified position in this list
     * @param index - index of the element to remove and return
     * @return The removed element from list
     */
    @Override
    public T remove(int index) {
        if (index >= this.length) throw new IndexOutOfBoundsException("Index is out of bounds");
        T toRemove = (T) this.array[index];
        for (int i = index; i < this.array.length-1; ++i) this.array[i] = this.array[i+1];
        ++this.freeMemory;
        --this.length;
        --this.lastFreeIndex;
        return toRemove;
    }

    /**
     * Removes the first occurrence of the specified
     * @param element - element to be removed from this list, if present
     * @return false if object is not present, else true
     */
    @Override
    public boolean remove(T element) {
        int itemIndex = this.indexOf(element);
        if (itemIndex == -1) return false;
        this.remove(itemIndex);
        return true;
    }

    /**
     * Removes all of the elements from this list. The list will be empty after this call returns.
     */
    @Override
    public void clear() {
        this.freeMemory = 10;
        this.array = new Object[this.freeMemory];
        this.lastFreeIndex = 0;
        this.length = 0;
    }

    /**
     * Returns true if this list contains the specified element
     * @param element - element whose presence in this list is to be tested
     * @return true if this list contains the specified element
     */
    @Override
    public boolean contains(T element) {
        for (Object item : this.array) {
            if (item == element) return true;
        }
        return false;
    }


    /**
     * Sorts this list according to their natural order
     */
    @Override
    public void sort() {
        this.quickSort(this, null, this.length);
    }

    /**
     * Sorts this list according to the order induced by the specified Comparator.
     * All elements in this list must be mutually comparable using the specified comparator
     * @param comparator - Comparator used to compare elements of the array
     */
    @Override
    public void sort(Comparator<? super T> comparator) {
        this.quickSort(this, comparator, this.length);
    }

    private void quickSort(ArrayList<T> elements, Comparator<? super T> comparator, int size) {
        if (size == 1 || size == 0) {
            return;
        } else if (size == 2) {
            if (comparator != null) {
                if (comparator.compare(elements.get(0), elements.get(0)) > 0) {
                    T temp = elements.get(0);
                    elements.set(0, elements.get(1));
                    elements.set(1, temp);
                }
            } else {
                if (((Comparable<T>) elements.get(0)).compareTo(elements.get(1)) > 0) {
                    T temp = elements.get(0);
                    elements.set(0, elements.get(1));
                    elements.set(1, temp);
                }
            }
            return;
        }
        int middle = size / 2;
        T pivot = elements.get(size / 2);
        ArrayList<T> lowerHalf = (ArrayList<T>) new ArrayList<>();
        ArrayList<T> upperHalf = (ArrayList<T>) new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            if (i == middle) continue;
            if (comparator == null) {
                if (((Comparable<T>) elements.get(i)).compareTo(pivot) < 0) lowerHalf.add(elements.get(i));
                else upperHalf.add(elements.get(i));
            } else {
                if (comparator.compare(elements.get(i), pivot) < 0) lowerHalf.add(elements.get(i));
                else upperHalf.add(elements.get(i));
            }
        }
        lowerHalf.sort(comparator);
        upperHalf.sort(comparator);
        lowerHalf.add(pivot);
        lowerHalf.add(upperHalf);
        this.array = lowerHalf.array;
        this.length = lowerHalf.length;
        this.freeMemory = lowerHalf.freeMemory;
        this.lastFreeIndex = lowerHalf.lastFreeIndex;
    }


    /**
     * Returns the size of the array
     * @return size of the array
     */
    @Override
    public int size() {
        return this.length;
    }

    /**
     * Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element
     * @param element - element to search for
     * @return The index of the last occurrence of the specified element in this list, or -1 if this list does not contain the element
     */
    @Override
    public int indexOf(T element) {
        for (int i = 0; i < this.array.length; ++i) {
            if (this.array[i] == element) return i;
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayList<T> arrayList = (ArrayList<T>) o;
        if (this.length != arrayList.length) return false;
        for (int i = 0; i < this.length; ++i) {
            if (this.array[i] != arrayList.array[i]) return false;
        }
        return true;
    }
}
