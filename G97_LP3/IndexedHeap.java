import java.util.Comparator;

/**
 * 
 * @author Rahul Aravind Mehalingam, Ramesh suthan palani, Anandan Sundar, Sanjana Ramakrishnan
 *
 */
public class IndexedHeap<T extends Index> extends BinaryHeap<T> {
	
	//build a priority queue with a given array
	IndexedHeap(T[] q, Comparator<T> comp) {
		super(q, comp);
	}
	
	// restore heap order property after the property of x has been decreased
	void decreaseKey(T x) {
		percolateUp(x.getIndex());
	}
	
	//override
	public void assignIndex(int srcIdx, int destIdx) {
		super.assignIndex(srcIdx, destIdx);
		pq[srcIdx].putIndex(srcIdx);
	}

}
