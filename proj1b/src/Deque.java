import java.util.List;

public interface Deque<T> {
    void addFirst(T item);

    void addLast(T item);

    T removeFirst();

    T removeLast();

    T get(int index);

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    List<T> toList();
}
