import java.util.*;
import java.util.Iterator;


public class CustomArrayList<T> implements List<T> {
    private static final int capacity = 10;
    private static Object[] arrayList = {};
    private static int size = 0;


    public CustomArrayList() {
        int CAPACITY = capacity;
        arrayList = new Object[capacity];
    }

    public CustomArrayList(int capacity) {
        if (capacity >= 0) {
            arrayList = new Object[capacity];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    capacity);
        }
    }

    private static void NewCapacity() {
        int CAPACITY = capacity * 3 / 2 + 1;
        Object[] newArrayList = new Object[CAPACITY];
        for (int i = 0; i < size; i++) {
            newArrayList[i] = arrayList[i];
            arrayList[i] = null;
        }
        arrayList = newArrayList;
    }

    private void shiftToLeft(int start) {
        size--;
        if (size <= 0) {
            return;
        }
        if (size != start) {
            System.arraycopy(arrayList, start + 1, arrayList, start, size - start);
        }
        arrayList[size] = null;
    }

    @Override
    public int size() {
        return size;//
    }

    @Override
    public boolean isEmpty() {
        return size == 0;//
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;//
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(arrayList, size);
    }

    @Override
    public Object[] toArray(Object[] a) {
        if (a.length < size) {
            return (Object[]) Arrays.copyOf(arrayList, size);
        }
        System.arraycopy(arrayList, 0, a, 0, size);
        if (size < a.length) {
            a[size] = null;
        }
        return a;
    }


    @Override
    public boolean add(Object o) {
        if (size >= capacity) {
            NewCapacity();
        }
        arrayList[size++] = o;
        return true;//
    }

    @Override
    public boolean remove(Object o) {
        if ((size == 0)) {
            return false;
        }
        int i;
        for (i = 0; i < size; i++) {
            if (arrayList[i] == null && o == null) {
                break;
            }
            if ((arrayList[i] != null) && (arrayList[i].equals(o))) {
                break;
            }
        }
        if (i < size) {
            shiftToLeft(i);
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        if (c == null) {
            return false;
        }
        if (c.isEmpty()) {
            return false;
        }
        for (Object item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        if (c == null) {
            return false;
        }
        if (c.isEmpty() || (index < 0)) {
            return false;
        }
        if (index > size) {
            index = size;
        }
        for (Object item : c) {
            add(index++, item);
        }
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i <= size; i++) {
            arrayList[i] = null;
        }
        size = 0;
    }

    @Override
    public T get(int index) {
        if (index >= 0 || index < size()) {
            return (T) arrayList[index];
        }
        return null;
    }

    @Override
    public Object set(int index, Object o) {
        if ((index < size) && (index >= 0)) {
            Object ob = arrayList[index];
            arrayList[index] = o;
            return ob;
        }
        return null;
    }

    @Override
    public void add(int index, Object o) {
        if (index < 0) {
            return;
        }
        if (size + 1 >= capacity) {
            NewCapacity();
        }
        if (index > size) {
            index = size;
        }
        if (size + 1 - index >= 0)
            System.arraycopy(arrayList, index, arrayList, index + 1, size + 1 - index);
        arrayList[index] = o;
        size++;
    }

    @Override
    public T remove(int index) {
        Object o = null;
        if ((index < size) && (index >= 0)) {
            o = get(index);
            shiftToLeft(index);
        }
        return (T) o;
    }

    @Override
    public int indexOf(Object o) {
        return indexOfRange(o, 0, size);//
    }

    int indexOfRange(Object o, int start, int end) {
        Object[] es = arrayList;
        if (o == null) {
            for (int i = start; i < end; i++) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = start; i < end; i++) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        return lastIndexOfRange(o, 0, size);//
    }

    int lastIndexOfRange(Object o, int start, int end) {
        Object[] es = arrayList;
        if (o == null) {
            for (int i = end - 1; i >= start; i--) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = end - 1; i >= start; i--) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        ListIterator<T> listIterator = new ListIterator<>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return size > cursor && arrayList[cursor] != null;
            }

            @Override
            public T next() {
                return (T) arrayList[cursor++];
            }

            @Override
            public boolean hasPrevious() {
                return cursor >= 1 && arrayList[cursor--] != null;
            }

            @Override
            public T previous() {
                return (T) arrayList[cursor--];
            }

            @Override
            public int nextIndex() {
                return cursor;
            }

            @Override
            public int previousIndex() {
                return cursor - 1;
            }

            @Override
            public void remove() {
                arrayList[cursor] = null;
            }

            @Override
            public void set(T t) {
                CustomArrayList.this.set(cursor - 1, t);
            }

            @Override
            public void add(T t) {
                if (size >= capacity) {
                    NewCapacity();
                }
                arrayList[cursor] = t;
                size++;
            }
        };
        return listIterator;
    }


    @Override
    public ListIterator listIterator(int index) {
        if (index < size && index == 0) {
            return null;
        }

        ListIterator<T> listIterator = new ListIterator<T>() {
            int cursor = index;

            @Override
            public boolean hasNext() {
                return size > cursor && arrayList[cursor] != null;
            }

            @Override
            public T next() {
                return (T) arrayList[cursor++];
            }

            @Override
            public boolean hasPrevious() {
                return arrayList[cursor--] != null && cursor >= 0;
            }

            @Override
            public T previous() {
                return (T) arrayList[cursor];
            }

            @Override
            public int nextIndex() {

                return cursor++;
            }

            @Override
            public int previousIndex() {
                return index - 1;
            }

            @Override
            public void remove() {
                arrayList[index] = null;
            }


            @Override
            public void set(T t) {
                CustomArrayList.this.set(index, t);
            }

            @Override
            public void add(T t) {
                if (size >= capacity) {
                    NewCapacity();
                }
                arrayList[index] = t;
                size++;
            }
        };
        return listIterator;
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            int temp = fromIndex;
            fromIndex = toIndex;
            toIndex = temp;
        }
        if ((fromIndex < 0) || (toIndex > size)) {
            return null;

        }
        List customArrayList = new CustomArrayList(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            customArrayList.add(arrayList[i]);
        }
        return customArrayList;
    }

    @Override
    public boolean retainAll(Collection c) {
        if (c == null) {
            clear();
            return true;
        }
        if (c.size() == 0) {
            clear();
            return true;
        }
        int i = 0;
        boolean modified = false;
        while (i < size) {
            if (c.contains(arrayList[i])) {
                i++;
            } else {
                shiftToLeft(i);
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection c) {
        if (c == null) {
            return false;
        }
        if ((c.size() == 0) || (size == 0)) {
            return false;
        }
        boolean modified = false;
        int i = 0;
        while (i < size) {
            if (c.contains(arrayList[i])) {
                shiftToLeft(i);
                modified = true;
            } else {
                i++;
            }
        }
        return modified;
    }

    @Override
    public boolean containsAll(Collection c) {
        if (c == null) {
            return false;
        }
        if (c.size() == 0) {
            return true;
        }
        if (c.size() > size()) {
            return false;
        }
        boolean modified = false;
        int i = 0;
        while (i < size) {
            if (c.contains(arrayList[i])) {
                i++;
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> it = new Iterator() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return arrayList[cursor] != null && size > cursor;
            }

            @Override
            public Object next() {
                return arrayList[cursor++];
            }
        };
        return it;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayList[i] + " ");
        }
        return "[" + sb + "]";
    }

}


