/**
 * A dynamic array implementation that can store any type of object.
 * @param <T> the type of object to be stored in the array.
 */

public class DynamicArray<T> {

    /**
     * Fixed size array of object T.
     */
    private T[] arr;
    /**
     * Size that arr will be initialized to.
     */
    private int size;

    /**
     * Constructs a new dynamic array with the specified size.
     * @param size the size of the array.
     */
    @SuppressWarnings("unchecked")
    public DynamicArray(int size) {
        arr = (T[]) new Object[size];
        this.size = size;
    }

    /**
     * Sets the object at the specified index in the array.
     * @param index the index of the object to be set.
     * @param obj the object to be set.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public void set(int index, T obj) {
        if (index >= 0 && index < size) {
            arr[index] = obj;
        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    /**
     * Returns the object at the specified index in the array.
     * @param index the index of the object to be returned.
     * @return the object at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public T get(int index) {
        if (index >= 0 && index < size) {
            return arr[index];
        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    /**
     * Returns the size of the array.
     * @return the size of the array.
     */
    public int size() {
        return size;
    }
}