import java.lang.reflect.Array;
import java.util.Comparator;

/**
 * ArrayList is an implementation of resizable array. Implements some of the list operations and permits all elements.
 * All ArrayLists have internal capacity, which tracks if new memory should be allocated.
 *
 * Be warned, that this ArrayList is not Synchronized.
 */
@SuppressWarnings("unchecked")
public class ArrayList<T extends Comparable<T>> implements List<T> {
    private T[] array;
    private int freeMemory;

    private int lastFreeIndex;

    final Class<T> objectType;

    /**
     * Constructs an empty list with an initial capacity of ten
     */
    ArrayList(Class<T> clazz) {
        this.freeMemory = 10;
        this.array = (T[]) Array.newInstance(clazz, this.freeMemory);
        this.objectType = clazz;
        this.lastFreeIndex = 0;
    }

    /**
     * Constructs a list containing the elements of the specified array, in the order they are placed
     * @param array - The array whose elements are to be placed into this list
     */
    ArrayList(Class<T> clazz, T[] array) {
        this.objectType = clazz;
        this.freeMemory = 10;
        this.array = (T[]) Array.newInstance(clazz, array.length+this.freeMemory);
        for (int i = 0; i < array.length; ++i) this.array[i] = array[i];
        this.lastFreeIndex = array.length;
    }

    private void instantiateNewMemory() {
        T[] copy = this.array;
        this.freeMemory = 15;
        this.array = (T[]) Array.newInstance(objectType, this.array.length + this.freeMemory);
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
        --this.freeMemory;
    }

    /**
     * Insert the specified element at the specified position. Shifts the element
     * @param index - place in an array, where element will be inserted
     * @param element - element, which will be added to array
     */
    @Override
    public void add(int index, T element) {
        if (index >= this.array.length) throw new IndexOutOfBoundsException("Index is out of bounds");
        if (this.freeMemory == 0) this.instantiateNewMemory();
        for (int i = this.array.length; i < index; --i) this.array[i] = this.array[i-1];
        this.array[index] = element;
        ++this.lastFreeIndex;
        --this.freeMemory;
    }

    /**
     * Returns the element at the specified position in this list
     * @param index - index of the element to return
     * @return The element at the specified position in the list
     */
    @Override
    public T get(int index) {
        if (index >= this.array.length) throw new IndexOutOfBoundsException("Index is out of bounds");
        return this.array[index];
    }

    /**
     * Remove and return the element at the specified position in this list
     * @param index - index of the element to remove and return
     * @return The removed element from list
     */
    @Override
    public T remove(int index) {
        if (index >= this.array.length) throw new IndexOutOfBoundsException("Index is out of bounds");
        T toRemove = this.array[index];
        for (int i = index; i < this.array.length-1; ++i) this.array[i] = this.array[i+1];
        ++this.freeMemory;
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
        ++this.freeMemory;
        --this.lastFreeIndex;
        return true;
    }

    /**
     * Removes all of the elements from this list. The list will be empty after this call returns.
     */
    @Override
    public void clear() {
        this.array = (T[]) Array.newInstance(objectType, 10);
        this.freeMemory = 10;
        this.lastFreeIndex = 0;
    }

    /**
     * Returns true if this list contains the specified element
     * @param element - element whose presence in this list is to be tested
     * @return true if this list contains the specified element
     */
    @Override
    public boolean contains(T element) {
        for (T item : this.array) {
            if (item == element) return true;
        }
        return false;
    }


    /**
     * Sorts this list according to the order induced by the specified Comparator.
     * All elements in this list must be mutually comparable using the specified comparator
     * @param comparator  - the Comparator used to compare list elements. A null value indicates that the elements natural ordering should be used
     */
    @Override
    public void sort(Comparator<? super T> comparator) {
        this.quickSort(this.array, comparator, this.array.length);
    }

    private T[] quickSort(T[] elements, Comparator<? super T> comparator, int size) {
        if (size == 1) {
            return elements;
        } else if (size == 2) {
            if (comparator != null) {
                if (comparator.compare(elements[0], elements[1]) > 0) {
                    T temp = elements[0];
                    elements[0] = elements[1];
                    elements[1] = temp;
                }
            } else {
                if (elements[0].compareTo(elements[1]) > 0) {
                    T temp = elements[0];
                    elements[0] = elements[1];
                    elements[1] = temp;
                }
            }
            return elements;
        }
        int middle = size / 2;
        T pivot = elements[size / 2];
        T[] left = (T[]) Array.newInstance(this.objectType, middle);
        for (int i = 0; i < middle; ++i) {
            left[i] = elements[i];
        }
        T[] right = (T[]) Array.newInstance(this.objectType, size-middle-1);
        for (int i = middle+1; i < size; ++i) right[i] = elements[i];
        left = this.quickSort(left, comparator, middle);
        right = this.quickSort(right, comparator, size-middle-1);
        for (int i = 0; i < middle; ++i) elements[i] = left[i];
        for (int i = middle+1; i < size; ++i) elements[i] = right[i];
        return elements;
    }


    /**
     * Returns the size of the array
     * @return size of the array
     */
    @Override
    public int size() {
        return this.array.length;
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
}
