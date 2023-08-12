public interface List<T> {
    void add(T element);
    void add(int index, T element);
    T get(int index);
    T remove(int index);
    boolean remove(T element);
    void clear();
    boolean contains(T element);
    void sort();
    int size();
    int indexOf(T element);
}
