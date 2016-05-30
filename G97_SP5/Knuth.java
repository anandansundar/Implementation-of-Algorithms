/**
 * @author Anandan Sundar, Rahul Aravind Mehalingam, Ramesh Suthan Palani, Sanjana Ramakrishnan
 *
 */
public class Knuth {

	Integer count = 0;

	public Knuth(Integer[] A) {
		
		for (int i = 0; i < A.length; ++i) {
			A[i] = i;
		}
	}

	public void permute(Integer[] A) {

		int n = A.length;
		int l = n - 1;
		int j = l - 1;

		visit(A, 1);
		
		while (true) {
			
			// finding j such that A[j] < A[j + 1]
			while (j > 0 && A[j] > A[j + 1] ) {
				j--;
			}
			
			//if j is 0, then stop		
			if (j == 0)
				break;
			
			// l is the max index such that a[j] < a[l]
			while (A[j] > A[l]) {
				l--;
			}
			
			// exchanging A[j] and A[l]
			swap(A, j, l);			
			
			int mid = (j + 1 + n)/2;

			int t = 0;
			
			// Reversing from A[j+1] to length of the array
		
			for (int i = j + 1; i < mid ; ++i) {
				int temp = A[i];
				A[i] = A[n - t - 1];
				A[n - t - 1] = temp;
				t++;
			}
			
			visit(A, 1);
			l = n - 1;
			j = l - 1;
		}
	}

	private void visit(Integer[] A, Integer from) {

		for (int i = from; i < A.length; ++i) {
			System.out.print(A[i]);
		}

		System.out.println();
		count++;

	}

	private void swap(Integer[] A, Integer a, Integer b) {
		Integer temp;
		temp = A[a];
		A[a] = A[b];
		A[b] = temp;
	}

	public static void main(String[] args) {
		
		Integer[] A = new Integer[10]; // Define size as required. Should be 1 greater than the number desired. 
		int n = A.length - 1;
		Knuth kn = new Knuth(A);
		Timer timer = new Timer();
		kn.permute(A);
		timer.end();
		System.out.println(timer);
		System.out.println("Total number of permutations for " + n + " digits is " + kn.count);

	}

}
