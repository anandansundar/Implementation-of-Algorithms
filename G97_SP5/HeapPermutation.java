/**
 * @author Anandan Sundar, Rahul Aravind Mehalingam, Ramesh Suthan Palani, Sanjana Ramakrishnan
 *
 */
public class HeapPermutation {

	public HeapPermutation(Integer[] A) {
		for (int i = 0; i < A.length; ++i) {
			A[i] = i + 1;
		}
	}
	
	Integer count = 0;
	
	public void visit(Integer[] A){
		for (Integer i : A){
			System.out.print(i);
		}
		System.out.println();
		count++;
	}

	public void heap_permute(Integer[] A, Integer n) {
		if (n == 1) {
			visit(A);
		} 
		else {
			for (int i = 0; i < n; ++i) {
				heap_permute(A, n - 1);
				if (n % 2 == 0) {
					swap(A, i, n - 1);
				} else {
					swap(A, 0, n - 1);
				}
			}
		}
	}

	private void swap(Integer[] A, Integer a, Integer b) {
		Integer temp;

		temp = A[a];
		A[a] = A[b];
		A[b] = temp;
	}

	public static void main(String[] args) {
			
		Integer[] A = new Integer[8]; //Define size as required. Array Size = number of digits in each permutation.
		Integer n = A.length;
		HeapPermutation heap = new HeapPermutation(A);
		Timer timer = new Timer();
		heap.heap_permute(A, n);
		timer.end();
		System.out.println(timer);
		System.out.println("Calculated permutations of " + n + " numbers");
		System.out.println("Total number of permutations : " + heap.count);
		
	}

}
