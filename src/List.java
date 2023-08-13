import java.util.Comparator;

public interface List<T> {
    void add(T element);
    void add(int index, T element);
    T get(int index);
    T remove(int index);
    boolean remove(T element);
    void clear();
    boolean contains(T element);
    void sort(Comparator<? super T> comparator);
    int size();
    int indexOf(T element);
}
