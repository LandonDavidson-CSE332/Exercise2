import java.lang.reflect.Array;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class BinaryMaxHeap <T extends Comparable<T>> implements MyPriorityQueue<T> {

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
        T finalItem = this.arr[i];
        // Calculate parents index to initialize while loop
        int parent_index = getParent(i);
        // Loop until value of parent is not greater than value of child or we reach the root
        while (this.arr[parent_index].compareTo(finalItem) > 0 && i != 0) {
            // Move value of parent to child index
            this.arr[i] = this.arr[parent_index];
            // Change parents index in itemToIndex hashmap
            this.itemToIndex.replace(this.arr[i], i);
            // Update i to current parent index
            i = parent_index;
            // Calculate new parent index
            parent_index = getParent(parent_index);
        }
        // Add the percolated item to itemToIndex hashmap
        this.itemToIndex.replace(this.arr[i], i);
        // Copy the original value to the hole we created
        this.arr[i] = finalItem;
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
            if (this.arr[i].compareTo(this.arr[lowestIndex]) > 0) {
                this.arr[i] = this.arr[lowestIndex];
                // Update child's index in itemToIndex hashmap
                this.itemToIndex.replace(this.arr[i], i);
            } else {
                // Else the min heap property has been restored, and we can break from the loop
                break;
            }
            // Set i to the lowest index to move down a level
            i = lowestIndex;
        }
        // Add percolated item to itemToIndex hashmap
        this.itemToIndex.replace(this.arr[i], i);
        // Copy saved value into the hole we created
        this.arr[i] = finalItem;
    }

    // copy all items into a larger array to make more room.
    private void resize(){
        T[] larger = (T[]) Array.newInstance(Comparable.class, arr.length*2);
        System.arraycopy(this.arr, 0, larger, 0, this.arr.length);
        this.arr = larger;
    }

    @Override
    public void insert(T item) {

    }

    @Override
    public T extract() {
        return null;
    }

    @Override
    public T peek() {
        return null;
    }

    @Override
    public void updatePriority(T item) {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public List<T> toList() {
        return List.of();
    }

    @Override
    public void remove(T item) {

    }
}
