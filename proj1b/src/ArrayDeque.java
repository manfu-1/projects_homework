import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private T[] items;
    private int head;
    private int tail;
    private static final int INIT_CAPACITY = 8;

    public ArrayDeque() {
        items = (T[]) new Object[INIT_CAPACITY];
        size = 0;
        head = 3; 
        tail = 4;
    }

    private int pp(int i) { return (i + 1) & (items.length - 1); } 
    private int mm(int i) { return (i - 1) & (items.length - 1); } 

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        if (size > 0) {
            int current = pp(head);
            for (int i = 0; i < size; i++) {
                newItems[i] = items[current];
                current = pp(current);
            }
        }
        items = newItems;
        head = capacity - 1; 
        tail = size;        
    }

    private void expandIfFull() {
        if (size == items.length) {
            resize(items.length * 2);
        }
    }

    private void shrinkIfSparse() {
        if (items.length >= 16 && size < items.length / 4) {
            resize(items.length / 2);
        }
    }

    @Override
    public void addFirst(T item) {
        expandIfFull();
        items[head] = item;
        head = mm(head);
        size++;
    }

    @Override
    public void addLast(T item) {
        expandIfFull();
        items[tail] = item;
        tail = pp(tail);
        size++;
    }

    @Override
    public T removeFirst() {
        if (size == 0) return null;
        head = pp(head); 
        T item = items[head];
        items[head] = null;
        size--;
        shrinkIfSparse();
        return item;
    }

    @Override
    public T removeLast() {
        if (size == 0) return null;
        tail = mm(tail);
        T item = items[tail];
        items[tail] = null; 
        size--;
        shrinkIfSparse();
        return item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) return null;
        return items[(head + 1 + index) & (items.length - 1)];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(get(i));
        }
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Deque)) return false;
        Deque<?> other = (Deque<?>) o;
        if (this.size() != other.size()) return false;
        
        Iterator<T> thisIter = this.iterator();
        Iterator<?> otherIter = ((Iterable<T>) other).iterator();
        while (thisIter.hasNext()) {
            T item1 = thisIter.next();
            Object item2 = otherIter.next();
            if (!Objects.equals(item1, item2)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
    
    private class ArrayDequeIterator implements Iterator<T> {
        private int curr;

        public ArrayDequeIterator() {
            curr = 0;
        }

        @Override
        public boolean hasNext() {
            return curr < size;
        }

        @Override
        public T next() {
            return get(curr++);
        }
    }
    
    public static void main(String[] args) {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(1);
        ad.addLast(2);
        Iterator<Integer> seer = ad.iterator();
        while (seer.hasNext()) {
            System.out.println(seer.next()); 
        }
        System.out.println(ad.toList()); // [1, 2]
        System.out.println(ad.removeFirst()); // 1
        System.out.println(ad.removeLast()); // 2
        System.out.println(ad.removeFirst()); // null
    }
}