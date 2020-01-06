/**
 *
 * @author Khanh Tran
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E> {
    public static final int CAPACITY=4;
    private E[] data;
    private int size=0;
    
    public ArrayList() { this(CAPACITY);}
    public ArrayList(int capacity) {
        data = (E[]) new Object[capacity];
    }
    /**
     * @return size  returns the array size 
     */
    public int size() { return size; }
    
    /**
     * Check if array is empty
     * @return true if array is empty 
     */
    public boolean isEmpty() { return size == 0; }
    
    /**
     * Get the element at given index
     * @param i  index of array to get element from
     * @return element at given index
     * @throws IndexOutOfBoundsException 
     */
    public E get(int i) throws IndexOutOfBoundsException {
        checkIndex(i, size);
        return data[i];
    }
    /**
     * Replace an element at given index
     * @param i  index of array element will replace
     * @param e  element that will replace element at index
     * @return the replaced element
     * @throws IndexOutOfBoundsException if index less than 0 or greater than array size
     */
    public E set(int i, E e) throws IndexOutOfBoundsException {
        checkIndex(i, size);
        E temp = data[i];
        data[i] = e;
        return temp;
    }
    /**
     * Add an element at given index
     * @param i  index of array element will be added to
     * @param e  element to be added
     * @throws IndexOutOfBoundsException if index less than 0 or greater than array size
     */
    public void add(int i, E e) throws IndexOutOfBoundsException {
        checkIndex(i, size +1);
        if (size == data.length) {
            resize(2*data.length);
        }
            
        for (int k=size-1; k>=i; k--){
            data[k+1] = data[k];
        }
        data[i] = e;
        size++;
    }
    /**
     * Remove and return element at index
     * @param i  remove at index i
     * @return the element being removed
     * @throws IndexOutOfBoundsException if index less than 0 or greater than array size
     */
    
    public E remove(int i) throws IndexOutOfBoundsException {
        checkIndex(i, size);
        E temp = data[i];
        for (int k=i; k < size-1; k++) {
            data[k] = data[k+1];
        }
        data[size-1] = null;
        size--;
        return temp;
    }
    /**
    * Check if index is within array boundaries
    * @param i  The index being checked
    * @param n  The current size of array
    * @throws IndexOutOfBoundsException if index less than 0 or greater than array size
    */
    protected void checkIndex(int i, int n) throws IndexOutOfBoundsException {
        if (i<0 || i>=n) {
            throw new IndexOutOfBoundsException("Illegal index: " +1);
        }
    }
    /**
     * Create a larger array to transfer old array into
     * @param capacity  The new array capacity
     */
    public void resize(int capacity) {
        E[] temp = (E[]) new Object[capacity];
        for (int k=0; k<size; k++) {
            temp[k] = data[k];
        }
        data=temp;
    }
    /**
    * Compare an instance of ArrayList to the calling ArrayList
    * @param e  The ArrayList being compared to
    * @return true if the two ArrayList instances are equivalent
    */
    public boolean equals(ArrayList e) {
        if (this.data == null || e.data == null) {
            return false;
        }
        
        if (this.size != e.size()) {
            return false;
        }
        
        Iterator iter = this.iterator();
        Iterator iter2 = e.iterator();
        for (int i=0; i<this.size; i++) {
            if (!iter.next().equals(iter2.next())) {
                return false;
            }
        }
        return true;
    }
    /**
    * Create an iterator for ArrayList 
    */
    private class ArrayIterator implements Iterator<E> {
        private int j=0;
        private boolean removable = false;
        
        public boolean hasNext() { return j < size;}
        
        public E next() throws NoSuchElementException {
            if (j == size) throw new NoSuchElementException("No next element");
            removable = true;
            return data[j++];
        }
        
        public void remove() throws IllegalStateException {
            if (!removable) throw new IllegalStateException("nothing to remove");
            ArrayList.this.remove(j-1);
            j--;
            removable = false;
        }
    }
    /**
    * @return return an iterator for the instance of ArrayList
    */  
    public Iterator<E> iterator() {
        return new ArrayIterator();
    }
}