import java.util.*;

public class MyTest {
    public static void main(String[] args) {
        List<String> list = new CustomArrayList<>();

    }
}

class CustomArrayList<T> implements List<T> {
    private static int capacity = 10;
    private static int CAPACITY;
    private static Object[] arrayList = {};
    private static int size = 0;


    public CustomArrayList() {
        this.CAPACITY = capacity;
        arrayList = new Object[capacity];
    }

    public CustomArrayList(int capacity) {
        if (capacity >= 0) {
            this.arrayList = new Object[capacity];
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
        Object[] newArray = new Object[size];
        System.arraycopy(arrayList, 0, newArray, 0, size);
        return newArray;//
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
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
            fastRemove(arrayList, i);
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
        for (int i = size; i >= index; i--) {
            arrayList[i + 1] = arrayList[i];
        }
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
    public ListIterator listIterator() {
        return null;
    }

    @Override
    public ListIterator listIterator(int index) {
        return null;
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
        List list = new CustomArrayList(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            list.add(arrayList[i]);
        }
        return list;
    }

    @Override
    public boolean retainAll(Collection c) {
        if (c == null) {
            return true;
        }
        if (c.size() == 0) {
            clear();
            return true;
        }
        int i = 0;
        boolean modyfied = false;
        while (i < size) {
            if (c.contains(arrayList[i])) {
                i++;
            } else {
                shiftToLeft(i);
                modyfied = true;
            }
        }
        return modyfied;
    }

    @Override
    public boolean removeAll(Collection c) {
        if (c == null) {
            return false;
        }
        if ((c.size() == 0) || (size == 0)) {
            return false;
        }
        boolean modyfied = false;
        int i = 0;
        while (i < size) {
            if (c.contains(arrayList[i])) {
                fastRemove(arrayList, i);
                modyfied = true;
            } else {
                i++;
            }
        }
        return modyfied;
    }

    @Override
    public boolean containsAll(Collection c) {
        if (c == null) {
            return false;
        }
        if (c.size() == 0) {
            return true;
        }
        for (Object e : c) {
            if (contains(e)) {
                ;
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public String toString() {
        System.out.print("[ ");
        for (int i = 0; i < size; i++) {
            System.out.print(arrayList[i] + " ");

        }
        return "]";
    }

    private void fastRemove(Object[] o, int i) {

        int newSize;
        if ((newSize = size - 1) > i)
            System.arraycopy(o, i + 1, o, i, newSize - i);
        o[size = newSize] = null;
    }

}


