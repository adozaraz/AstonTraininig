import java.lang.reflect.Array;
import java.util.Iterator;

@SuppressWarnings("unchecked")
public class ArrayList<T> implements List<T> {
    private T[] array;
    private int freeMemory;
    private int length;

    final Class<T> objectType;

    ArrayList(Class<T> clazz) {
        this.array = (T[]) Array.newInstance(clazz, 10);
        this.objectType = clazz;
        this.length = 0;
        this.freeMemory = 10;
    }

    private void instantiateNewMemory() {
        T[] copy = this.array;
        this.array = (T[]) Array.newInstance(objectType, this.length + 15);
        for (int i = 0; i < this.length; ++i) this.array[i] = copy[i];
        this.freeMemory = 15;
    }

    @Override
    public void add(T element) {
        if (this.freeMemory == 0) {
            this.instantiateNewMemory();
        }
        this.array[this.length] = element;
        ++this.length;
        --this.freeMemory;
    }

    @Override
    public void add(int index, T element) {
        if (index >= this.length) throw new IndexOutOfBoundsException("Index is out of bounds");
        if (this.freeMemory == 0) this.instantiateNewMemory();
        for (int i = this.length; i < index; --i) this.array[i] = this.array[i-1];
        this.array[index] = element;
        ++this.length;
        --this.freeMemory;
    }

    @Override
    public T get(int index) {
        if (index >= this.length) throw new IndexOutOfBoundsException("Index is out of bounds");
        return this.array[index];
    }

    @Override
    public T remove(int index) {
        if (index >= this.length) throw new IndexOutOfBoundsException("Index is out of bounds");
        T toRemove = this.array[index];
        for (int i = index; i < this.length-1; ++i) this.array[i] = this.array[i+1];
        return toRemove;
    }

    @Override
    public boolean remove(T element) {
        int itemIndex = this.indexOf(element);
        if (itemIndex == -1) return false;
        this.remove(itemIndex);
        return true;
    }

    @Override
    public void clear() {
        this.array = (T[]) Array.newInstance(objectType, 10);
        this.freeMemory = 10;
        this.length = 0;
    }

    @Override
    public boolean contains(T element) {
        for (T item : this.array) {
            if (item == element) return true;
        }
        return false;
    }

    @Override
    public void sort() {

    }

    @Override
    public int size() {
        return this.length;
    }

    @Override
    public int indexOf(T element) {
        for (int i = 0; i < this.length; ++i) {
            if (this.array[i] == element) return i;
        }
        return -1;
    }
}
