import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Iterator;

/**
 * ArrayList
 * Created to imitate ArrayList implemented by Java
 * @param <T> clazz - Pass class type of your data
 */
@SuppressWarnings("unchecked")
public class ArrayList<T extends Comparable<T>> implements List<T> {
    private T[] array;
    private int freeMemory;

    private int lastFreeIndex;

    final Class<T> objectType;

    ArrayList(Class<T> clazz) {
        this.freeMemory = 10;
        this.array = (T[]) Array.newInstance(clazz, this.freeMemory);
        this.objectType = clazz;
        this.lastFreeIndex = 0;
    }

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

    @Override
    public void add(T element) {
        if (this.freeMemory == 0) {
            this.instantiateNewMemory();
        }
        this.array[this.lastFreeIndex] = element;
        ++this.lastFreeIndex;
        --this.freeMemory;
    }

    @Override
    public void add(int index, T element) {
        if (index >= this.array.length) throw new IndexOutOfBoundsException("Index is out of bounds");
        if (this.freeMemory == 0) this.instantiateNewMemory();
        for (int i = this.array.length; i < index; --i) this.array[i] = this.array[i-1];
        this.array[index] = element;
        ++this.lastFreeIndex;
        --this.freeMemory;
    }

    @Override
    public T get(int index) {
        if (index >= this.array.length) throw new IndexOutOfBoundsException("Index is out of bounds");
        return this.array[index];
    }

    @Override
    public T remove(int index) {
        if (index >= this.array.length) throw new IndexOutOfBoundsException("Index is out of bounds");
        T toRemove = this.array[index];
        for (int i = index; i < this.array.length-1; ++i) this.array[i] = this.array[i+1];
        ++this.freeMemory;
        --this.lastFreeIndex;
        return toRemove;
    }

    @Override
    public boolean remove(T element) {
        int itemIndex = this.indexOf(element);
        if (itemIndex == -1) return false;
        this.remove(itemIndex);
        ++this.freeMemory;
        --this.lastFreeIndex;
        return true;
    }

    @Override
    public void clear() {
        this.array = (T[]) Array.newInstance(objectType, 10);
        this.freeMemory = 10;
        this.lastFreeIndex = 0;
    }

    @Override
    public boolean contains(T element) {
        for (T item : this.array) {
            if (item == element) return true;
        }
        return false;
    }

    @Override
    public void sort(Comparator<? super T> comparator) {
        this.quickSort(this.array, comparator, this.array.length);
    }

    private T[] quickSort(T[] elements, Comparator<? super T> comparator, int size) {
        if (size == 1) {
            return elements;
        } else if (size == 2) {
            if (comparator.compare(elements[0], elements[1]) > 0) {
                T temp = elements[0];
                elements[0] = elements[1];
                elements[1] = temp;
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


    @Override
    public int size() {
        return this.array.length;
    }

    @Override
    public int indexOf(T element) {
        for (int i = 0; i < this.array.length; ++i) {
            if (this.array[i] == element) return i;
        }
        return -1;
    }
}
