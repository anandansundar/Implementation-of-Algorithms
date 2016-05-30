import java.util.Comparator;

/**
 * 
 * @author Rahul Aravind Mehalingam, Ramesh suthan palani, Anandan Sundar, Sanjana Ramakrishnan
 *
 */
public class BinaryHeap<T> implements PQ<T> {
	
	T[] pq;
	Comparator<T> c;
	private int size;
	
	// Build a priority queue with a given array
	BinaryHeap(T[] q, Comparator<T> comp) {
		pq = q;
		size = q.length - 1;
		c = comp;
		buildHeap();
	}
	
	// create an empty priority queue of given maximum size
	public BinaryHeap(int n, Comparator<T> comp) {
		c = comp;
		pq = (T[]) new Object[n + 1];
		size = 0;
	}
	
	// Insert new element into the heap
	public void insert(T x) {
		add(x);
	}
	
	//delete the root element in the heap
	public T deleteMin() {
		if(size == 0) return null;
		
		return remove();
	}
	
	// Return the root element in the heap which would be the maximum or minimum 
	// element depending upon the comparator
	
	public T min() {
		return peek();
	}
	
	// add an element into the heap
	
	public void add(T x) {
		
		// resize the size of the heap if it has reached its limit
		if(size == pq.length - 1) {
			T[] temp = pq;
			pq = (T[]) new Object[temp.length * 2 + 1];
			for(int i = 0; i < temp.length; i++) {
				pq[i] = temp[i];
			}
		}
		
		pq[++size] = x;
		percolateUp(size);
	}
	
	void percolateUp(int idx) {
		int hole = idx;
		T elt = pq[idx];
		
		pq[0] = elt;
		while(c.compare(pq[0], pq[hole / 2]) < 0) {
			assignIndex(hole, hole / 2);
			hole /= 2;
		}
		
		assignIndex(hole, 0);
	}
	
	// Remove the minimum or maximum element based on the comparator type
	public T remove() {
		T val = peek();
		
		pq[1] = pq[size--];
		percolateDown(1);
		
		return val;
	}
	
	// Remove the minimum or maximum element based on the comparator type
	public T peek() {
		if(size == 0) {
			System.out.println("Heap has no elements");
			return null;
		}
		
		return pq[1];
	}
	
	void percolateDown(int idx) {
		pq[0] = pq[idx];
		
		while(2 * idx <= size) {
			int child = 2 * idx;
			
			// get the minimum of the left child and right child
			if(child < size && c.compare(pq[child], pq[child + 1]) > 0) child++;
			
			if(c.compare(pq[child], pq[0]) < 0) {
				assignIndex(idx, child);
			} else {
				break;
			}
			
			idx = child;
		}
		
		assignIndex(idx, 0);
	}
	
	// build a heap
	void buildHeap() {
		for(int i = size / 2; i > 0; i--) {
			percolateDown(i);
		}
	}
	
	//check if the heap is empty or not
	public boolean isEmpty() {
		return size == 0;
	}
	
	// assign the index
	public void assignIndex(int srcIdx, int destIdx) {
		pq[srcIdx] = pq[destIdx];
	}
	
	/* sort array A[1..n].  A[0] is not used. 
    Sorted order depends on comparator used to buid heap.
    min heap ==> descending order
    max heap ==> ascending order
	 */
	public static<T> void heapSort(T[] A, Comparator<T> comp) {
		BinaryHeap<T> heap = new BinaryHeap<>(A, comp);
		
		for(int i = heap.size; i >= 0; i--) {
			T temp = A[1];
			A[1] = A[i];
			A[i] = temp;
			
			heap.size--;
			heap.percolateDown(1);
		}
		
		for(T elt : heap.pq) {
			System.out.println();
		}
	}


}
