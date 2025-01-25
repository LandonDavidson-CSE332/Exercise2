import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class TopKHeap<T extends Comparable<T>> {
    private BinaryMinHeap<T> topK; // Holds the top k items
    private BinaryMaxHeap<T> rest; // Holds all items other than the top k
    private int size; // Maintains the size of the data structure
    private final int k; // The value of k
    private Map<T, MyPriorityQueue<T>> itemToHeap; // Keeps track of which heap contains each item.
    
    // Creates a topKHeap for the given choice of k.
    public TopKHeap(int k){
        topK = new BinaryMinHeap<>();
        rest = new BinaryMaxHeap<>();
        size = 0;
        this.k = k;
        itemToHeap = new HashMap<>();
    }

    // Returns a list containing exactly the
    // largest k items. The list is not necessarily
    // sorted. If the size is less than or equal to
    // k then the list will contain all items.
    // The running time of this method should be O(k).
    public List<T> topK(){
        return null;
    }

    // Add the given item into the data structure.
    // The running time of this method should be O(log(n)+log(k)).
    public void insert(T item){
        // Add item into the topK min heap and itemToHeap hashmap
        this.topK.insert(item);
        this.itemToHeap.put(item, this.topK);
        // Now remove the minimum value from topK and itemToHeap
        T kickedItem = this.topK.extract();
        this.itemToHeap.remove(item);
        // Then add it to the rest max heap and back to itemToHeap
        this.itemToHeap.put(item, rest);
        this.rest.insert(kickedItem);
    }

    // Indicates whether the given item is among the 
    // top k items. Should return false if the item
    // is not present in the data structure at all.
    // The running time of this method should be O(1).
    // We have provided a suggested implementation,
    // but you're welcome to do something different!
    public boolean isTopK(T item){
        return itemToHeap.get(item) == topK;
    }

    // To be used whenever an item's priority has changed.
    // The input is a reference to the items whose priority
    // has changed. This operation will then rearrange
    // the items in the data structure to ensure it
    // operates correctly.
    // Throws an IllegalArgumentException if the item is
    // not a member of the heap.
    // The running time of this method should be O(log(n)+log(k)).
    public void updatePriority(T item){
        // Find what heap item is in
        MyPriorityQueue<T> heap = this.itemToHeap.get(item);
        // If heap is null than item isn't in either heap so throw an error
        if (heap == null) {
            throw new IllegalArgumentException();
        }
        // Update item priority
        heap.updatePriority(item);
    }

    // Removes the given item from the data structure
    // throws an IllegalArgumentException if the item
    // is not present.
    // The running time of this method should be O(log(n)+log(k)).
    public void remove(T item){
        // Find what heap the item is currently in
        MyPriorityQueue<T> heap = this.itemToHeap.get(item);
        // If heap is null than item isn't in either and throw an error
        if (heap == null) {
            throw new IllegalArgumentException();
        }
        // Remove the item from the heap since we don't need it
        heap.remove(item);
        // If heap is rest then we can return now since we are done
        if (heap == this.rest) {
            return;
        }
        // Otherwise we have to add the min item from rest to topK
        T promotedItem = rest.extract();
        topK.insert(promotedItem);
    }
}
