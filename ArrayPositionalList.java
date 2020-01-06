/**
 *
 * @author Khanh Tran
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * An interface for positional lists.
 */
public class ArrayPositionalList<E> implements PositionalList<E> {

    private static class ArrayPos<E> implements Position<E> {

        private E element; 
        private int index;	

        public ArrayPos(E e, int i) {
            element = e;
            int index = i;
        }

        public E getElement() throws IllegalStateException {
            return element;
        }

        public void setElement(E e) {
            element = e;
        }
        
		
        public void setIndex(int i) {
            index = i;
	}
		
	public int getIndex() {
            return index;
	}
		
        public String toString(){
            return element.toString();
        }
    } 

    private static final int CAPACITY=20;
    private ArrayPos[] data;					
    private int size = 0;                         

    public ArrayPositionalList() {this(CAPACITY);}
	
    public ArrayPositionalList(int capacity) {
        data = new ArrayPos[capacity];
    }

    public ArrayPos<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof ArrayPos)) throw new IllegalArgumentException("Invalid p");
        ArrayPos<E> node = (ArrayPos<E>) p;
        return node;
    }
    
    private Position<E> position(ArrayPos<E> node) { 
        return node;
    }
    /**
     * Returns the number of elements in the list.
     * @return number of elements in the list
     */
    public int size() { return size; }
    /**
     * Tests whether the list is empty.
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() { return size == 0; }
    /**
     * Returns the first Position in the list.
     *
     * @return the first Position in the list (or null, if empty)
     */
    public Position<E> first() {
        return data[0];
    }
    /**
     * Returns the last Position in the list.
     *
     * @return the last Position in the list (or null, if empty)
     */
    public Position<E> last() {
        return data[size-1];
    }
    /**
     * Returns the Position immediately before Position p.
     * @param p   a Position of the list
     * @return the Position of the preceding element (or null, if p is first)
     * @throws IllegalArgumentException if p is not a valid position for this list
     */
    public Position<E> before(Position<E> p) throws IllegalArgumentException {
        ArrayPos<E> node = validate(p);
        return position(data[node.getIndex()-1]);
    }
    /**
     * Returns the Position immediately after Position p.
     * @param p   a Position of the list
     * @return the Position of the following element (or null, if p is last)
     * @throws IllegalArgumentException if p is not a valid position for this list
     */
    public Position<E> after(Position<E> p) {
        ArrayPos<E> node = validate(p);
        return position(data[node.getIndex()+1]);
    }
    /**
     * Inserts an element at the front of the list.
     *
     * @param e the new element
     * @return the Position representing the location of the new element
     */
    public Position<E> addFirst(E e) {
        for (int k=size-1; k>=0; k--) {
            data[k].setIndex(k+1);
            data[k+1] = data[k];
	}
	data[0] = new ArrayPos<>(e, 0);
        size++;
	return data[0];
    }
    /**
     * Inserts an element at the back of the list.
     *
     * @param e the new element
     * @return the Position representing the location of the new element
     */
    public Position<E> addLast(E e) {
        data[size] = new ArrayPos<>(e, size);
        size++;
	return data[size];
    }
    /**
     * Inserts an element immediately before the given Position.
     *
     * @param p the Position before which the insertion takes place
     * @param e the new element
     * @return the Position representing the location of the new element
     * @throws IllegalArgumentException if p is not a valid position for this list
     */
    public Position<E> addBefore(Position<E> p, E e) 
                                         throws IllegalArgumentException {
        ArrayPos<E> node = validate(p);
        
        for (int k=size-1; k>=node.getIndex()-1; k--) {
            data[k].setIndex(k+1);
            data[k+1] = data[k];
        }
        data[node.getIndex()-1] = new ArrayPos<>(e, node.getIndex()-1);
        size++;
        return data[node.getIndex()-1];
    }
    /**
     * Inserts an element immediately after the given Position.
     *
     * @param p the Position after which the insertion takes place
     * @param e the new element
     * @return the Position representing the location of the new element
     * @throws IllegalArgumentException if p is not a valid position for this list
     */
    public Position<E> addAfter(Position <E> p, E e) 
                                         throws IllegalArgumentException {
        ArrayPos<E> node = validate(p);
        
        for (int k=size-1; k>=node.getIndex()+1; k--) {
            data[k].setIndex(k+1);
            data[k+1] = data[k];
        }
        data[node.getIndex()+1] = new ArrayPos<>(e, node.getIndex()+1);
        size++;
        return data[node.getIndex()+1];
    }
    /**
     * Replaces the element stored at the given Position and returns the replaced element.
     *
     * @param p the Position of the element to be replaced
     * @param e the new element
     * @return the replaced element
     * @throws IllegalArgumentException if p is not a valid position for this list
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        ArrayPos<E> node = validate(p);
        E answer = node.getElement();
        data[node.getIndex()] = new ArrayPos<>(e, node.getIndex());
        return answer;
    }
    /**
     * Removes the element stored at the given Position and returns it.
     * The given position is invalidated as a result.
     *
     * @param p the Position of the element to be removed
     * @return the removed element
     * @throws IllegalArgumentException if p is not a valid position for this list
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
        ArrayPos<E> node = validate(p);
    
        for (int k=node.getIndex(); k < size-1; k++) {
            data[k+1].setIndex(k);
            data[k] = data[k+1];
        }
        data[size-1] = null;
        size--;
        E answer = node.getElement();
        return answer;
    }
    /**
     * Create the iterator for positions
     */
    private class PositionIterator implements Iterator<Position<E>> {
        private Position<E> cursor = first();   
        private Position<E> recent = null;       
        public boolean hasNext() { return (cursor != null);  }
        public Position<E> next() throws NoSuchElementException {
            if (cursor == null) throw new NoSuchElementException("nothing left");
            recent = cursor;           
            cursor = after(cursor);
            return recent;
        }

        public void remove() throws IllegalStateException {
            if (recent == null) throw new IllegalStateException("nothing to remove");
            ArrayPositionalList.this.remove(recent);         
            recent = null;               
        }
    } 
    /**
     * Constructing iterator for positions
     */
    private class PositionIterable implements Iterable<Position<E>> {
        public Iterator<Position<E>> iterator() { return new PositionIterator(); }
    } 
    /**
     * Returns the positions of the list in iterable form from first to last.
     * @return iterable collection of the list's positions
     */
    public Iterable<Position<E>> positions() {
        return new PositionIterable();       
    }
    /**
     * Create iterator for elements
     */
    public class ElementIterator implements Iterator<E> {
        Iterator<Position<E>> posIterator = new PositionIterator();
        public boolean hasNext() { return posIterator.hasNext(); }
        public E next() { return posIterator.next().getElement(); }
        public void remove() { posIterator.remove(); }
    }
    /**
     * Returns an iterator of the elements stored in the list.
     * @return iterator of the list's elements
     */
    public Iterator<E> iterator() { return new ElementIterator(); }
    /**
     * Driver for the ArrayPositionalList
     */
    public static void main(String[] args) {
        //Create PositionalList that stores 1-10 in ascending order
        ArrayPositionalList array = new ArrayPositionalList();
        array.addFirst(10);
        array.addFirst(9);
        array.addFirst(8);
        array.addFirst(7);
        array.addFirst(6);
        array.addFirst(5);
        array.addFirst(4);
        array.addFirst(3);
        array.addFirst(2);
        array.addFirst(1);
        
        //Declare cursor and assign to first item
        Position cursor = array.first();
        //Display initial results
        System.out.print("Initial list: ");
        Iterator iter = array.iterator();
        while (iter.hasNext()) {
            System.out.print(iter.next() + ", ");
        }
        System.out.println();
        System.out.println("Cursor location: " + cursor.getElement());

        //Move cursor to the 5th position
        cursor = array.data[4];
        //Display cursor location
        System.out.println("Cursor location: " + cursor.getElement());
        
        //Add the number 8 before cursor and number 2 after cursor
        array.addBefore(cursor, 8);
        array.addAfter(cursor, 2);
                      
        //Move cursor backward 2 positions;
        cursor = array.data[2];
        //Display cursor location
        System.out.println("Cursor location: " + cursor.getElement());
       
        //Change number after cursor to 0, delete the item before cursor
        array.set(array.data[3], 0);
        array.remove(array.data[1]);
        //Display final results
        System.out.print("Final list: ");
        iter = array.iterator();
        while (iter.hasNext()) {
            System.out.print(iter.next() + ", ");
        }
        System.out.println();
        //Iterate through list of elements and delete positions with elements
        //less than 3, then display results
        iter = array.iterator();
        while (iter.hasNext()) {
            int temp = (int)iter.next();
            if (temp<3) {
                iter.remove();
            }
        }
        
        System.out.println("Final list after removal of all values less than 3");
        iter = array.iterator();
        while (iter.hasNext()) {
            System.out.print(iter.next() + ", ");
        }
        System.out.println();
    }
}
