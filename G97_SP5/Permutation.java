/**
 * @author Anandan Sundar, Rahul Aravind Mehalingam, Ramesh Suthan Palani, Sanjana Ramakrishnan
 *	Take 2 algorithm
 */
public class Permutation {

	Integer count = 0;

	public Permutation(Integer[] A) {
		for (int i = 0; i < A.length; ++i) {
			A[i] = i + 1;
		}
	}

	private void permute(Integer[] A, Integer i) {
		
		if (i == 0) {
			visit(A);
		}

		else {
			for (int j = 0; j <= i; ++j) {
				
				swap(A, i, j);
				permute(A, i - 1);
				swap(A, i, j);
				
			}
		}
	}


	private void visit(Integer[] A) {

		for (int i = 0; i < A.length; ++i) {
			System.out.print(A[i]);
		}

		System.out.println();
		count++;

	}
	
	private void swap ( Integer [] A, Integer a, Integer b) {
		Integer temp;
		
		temp = A[a];
		A[a] = A[b];
		A[b] = temp;
	}
	
	public static void main(String[] args) {

		Integer[] A = new Integer[8]; // Define size as required. Array Size = number of digits in each permutation.
		Permutation per = new Permutation(A);
		int n = A.length;
		Timer timer =  new Timer();
		per.permute(A, n - 1);
		timer.end();
		System.out.println(timer);
		System.out.println("Calculated permutations of " + n + " numbers");
		System.out.println("Total number of permutations : " + per.count);
	}

}
