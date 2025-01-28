# Exercise2: Heaps

There are 6 project files here, 3 of which were provided (Client.java, ChessPlayer.java, and MyPriorityQueue.java), and the other 3 were written by by me.

- BinaryMinHeap: A min heap implemented using an array
  
  - percolateUp: Repeatedly moves a nodes parent lower in the tree when it is greater than the given objects value, and finally inserts the given value at the final swapped parent's node
  
  - percolateDown: Repeatedly move the lowest value child higher in the tree if it is less than the given objects value, and finally inserts the given value at the final swapped child's node
  
  - insert: Put the given value at the end of the array and percolateUp() to restore the heap order property
  
  - extract: Return the minimum value (root of the array) and percolateDown() to restore the heap property
  
  - remove: Remove and return the given item from the heap and percolateDown() to restore heap property
  
  - updatePriority: If the given item's value is less than its parents then percolateUp(), otherwise percolateDown()

- BinaryMaxHeap: Exact same as BinaryMinHeap but it returns the maximum value. Accomplished by simply flipping the inequalities in percolateUp(), percolateDown(), and updatePriority()

- TopKHeap: Takes an argument k in the constructor defining how many of the top values to store. Uses a BinaryMinHeap to store the top k values so we can easily demote the bottom one when needed. Uses a BinaryMaxHeap to store the remaining values so we can easily promote the top one when needed.