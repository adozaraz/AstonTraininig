package ArrayList;

import java.util.Comparator;

public interface List<T> {
    void add(T element);
    void add(int index, T element);
    void add(List<T> list);
    T get(int index);
    void set(int index, T element);
    T remove(int index);
    boolean remove(T element);
    void clear();
    boolean contains(T element);
    void sort();
    void sort(Comparator<? super T> comparator);
    int size();
    int indexOf(T element);
}
