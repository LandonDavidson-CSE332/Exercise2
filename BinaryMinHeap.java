import java.lang.reflect.Array;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class BinaryMinHeap <T extends Comparable<T>> implements MyPriorityQueue<T> {
    private int size; // Maintains the size of the data structure
    private T[] arr; // The array containing all items in the data structure
                     // index 0 must be utilized
    private Map<T, Integer> itemToIndex; // Keeps track of which index of arr holds each item.

    public BinaryMinHeap(){
        // This line just creates an array of type T. We're doing it this way just
        // because of weird java generics stuff (that I frankly don't totally understand)
        // If you want to create a new array anywhere else (e.g. to resize) then
        // You should mimic this line. The second argument is the size of the new array.
        arr = (T[]) Array.newInstance(Comparable.class, 10);
        size = 0;
        itemToIndex = new HashMap<>();
    }

    // Return the index of the given index's parent
    private int getParent(int i){
        return i / 2;
    }

    // Return index of i's left child
    private int getLeftChild(int i){
        return i * 2 + 1;
    }
    // Return index of i's right child
    private int getRightChild(int i){
        return i * 2 + 2;
    }

    // move the item at index i "rootward" until
    // the heap property holds
    private void percolateUp(int i){
        // Save current item at i to copy after percolate up
        T finalItem = arr[i];
        // Calculate parents index to initialize while loop
        int parent_index = getParent(i);
        // Loop until value of parent is not greater than value of child
        while (arr[parent_index].compareTo(arr[i]) > 0) {
            // Move value of parent to child index
            arr[i] = arr[parent_index];
            // Move index to parent index
            i = parent_index;
            // Calculate new parent index
            parent_index = getParent(i);
        }
        // Copy the original value to the hole we created
        arr[i] = finalItem;
    }

    // move the item at index i "leafward" until
    // the heap property holds
    private void percolateDown(int i){
        // Save value at i to be copied after loop
        T finalItem = arr[i];
        // Infinite while loop to be broken out of by interior if statements
        while (true) {
            // Find and store both children's indices, assign -1 if out of current size bounds
            int leftIndex = getLeftChild(i) >= this.size ? -1 : getLeftChild(i);
            int rightIndex = getRightChild(i) >= this.size ? -1 : getRightChild(i);
            int lowestIndex;
            // If left child is out of bounds then i is a leaf, and we are done
            if (leftIndex == -1) {
                break;
            }
            // If only right child is out of bounds set the lowest child as left
            if (rightIndex == -1) {
                lowestIndex = leftIndex;
            } else {
                // If both are in bounds then find child with the lowest value
                lowestIndex = this.arr[leftIndex].compareTo(this.arr[rightIndex]) < 0 ? leftIndex : rightIndex;
            }
            // If current value greater than the lowest child's, copy child to current index
            if (arr[i].compareTo(arr[lowestIndex]) > 0) {
                arr[i] = arr[lowestIndex];
            } else {
                // Else the min heap property has been restored, and we can break from the loop
                break;
            }
            // Set i to the lowest index to move down a level
            i = lowestIndex;
        }
        // Copy saved value into the hole we created
        this.arr[i] = finalItem;
    }

    // copy all items into a larger array to make more room.
    private void resize(){
        T[] larger = (T[]) Array.newInstance(Comparable.class, arr.length*2);
        System.arraycopy(this.arr, 0, larger, 0, this.arr.length);
        this.arr = larger;
    }

    public void insert(T item){
        // If array is full then resize
        if (this.size == this.arr.length) {
            resize();
        }
        // Put item in the first empty spot of the array
        this.arr[size] = item;
        // Percolate the new item up
        percolateUp(size);
        // Increment size
        this.size++;
    }


    public T extract(){
        // If array is empty throw an IllegalStateException
        if (this.size == 0) {
            throw new IllegalStateException("The min heap is empty");
        }
        // Decrement size
        this.size--;
        // Remove the current root from the itemToIndex hashmap
        this.itemToIndex.remove(this.arr[0]);
        // Store the root node value
        T minVal = this.arr[0];
        // Copy value at end of array to the head
        this.arr[0] = this.arr[this.size];
        // Percolate new root down
        percolateDown(0);
        // Return stored minimum value
        return minVal;
    }

    // Remove the item at the given index.
    // Make sure to maintain the heap property!
    private T remove(int index){
        // If array is empty throw an IllegalStateException
        if (this.size == 0) {
            throw new IllegalStateException("The min heap is empty");
        }
        // Decrement size
        this.size--;
        // Remove the item at index from the itemToIndex hashmap
        this.itemToIndex.remove(this.arr[index]);
        // Store the relevant nodes data
        T targetVal = this.arr[index];
        // Copy value at the end of the array to the target index
        this.arr[index] = this.arr[size];
        // Percolate the new node down
        percolateDown(index);
        // Return the stored target value
        return targetVal;
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public void remove(T item){
        remove(itemToIndex.get(item));
    }

    // Determine whether to percolate up/down
    // the item at the given index, then do it!
    private void updatePriority(int index){
    }

    // This method gets called after the client has 
    // changed an item in a way that may change its
    // priority. In this case, the client should call
    // updatePriority on that changed item so that 
    // the heap can restore the heap property.
    // Throws an IllegalArgumentException if the given
    // item is not an element of the priority queue.
    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public void updatePriority(T item){
	if(!itemToIndex.containsKey(item)){
            throw new IllegalArgumentException("Given item is not present in the priority queue!");
	}
        updatePriority(itemToIndex.get(item));
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public boolean isEmpty(){
        return size == 0;
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public int size(){
        return size;
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public T peek(){
        return arr[0];
    }
    
    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public List<T> toList(){
        List<T> copy = new ArrayList<>();
        for(int i = 0; i < size; i++){
            copy.add(i, arr[i]);
        }
        return copy;
    }

    // For debugging
    public String toString(){
        if(size == 0){
            return "[]";
        }
        String str = "[(" + arr[0] + " " + itemToIndex.get(arr[0]) + ")";
        for(int i = 1; i < size; i++ ){
            str += ",(" + arr[i] + " " + itemToIndex.get(arr[i]) + ")";
        }
        return str + "]";
    }
    
}
