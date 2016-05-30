/**
 * @author Anandan Sundar, Rahul Aravind Mehalingam, Ramesh Suthan Palani, Sanjana Ramakrishnan
 *
 */
public class PermutationNK {
	int Pn;
	int Pk;

	public PermutationNK(int n, int k) {
		this.Pn = n;
		this.Pk = k;
	}

	public void Visit(int[] A) {

		for (int i = 0; i < A.length; i++) {
			System.out.print(A[i]);
		}

		System.out.println();
	}

	public void swap(int A[], int i, int j) {
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}

	public void HeapsPermutation(int n, int[] A) {
		if (n == 1) {
			Visit(A);
		} 
		 else {
			for (int i = 0; i < n; ++i) {
				HeapsPermutation(n - 1, A);
				if (n % 2 == 0) {
					swap(A, i, n - 1);
				} else {
					swap(A, 0, n - 1);
				}

			}
			
		}
	}

	public void visitCombination(boolean[] A) {

		System.out.print("Generate Permutation for the combination: ");

		int[] perm = new int[Pk];
		int j = 0;

		for (int i = 0; i < A.length; i++) {
			if (A[i]) {
				perm[j++] = i;
				System.out.print(i);
			}
		}
		
		System.out.println();
		HeapsPermutation(Pk, perm);

	}

	public void permutationNK(boolean A[], int n, int k) {

		if (k > n) {
			return;
		} 
		
		else if (k == 0) {
			visitCombination(A);
		} 
		
		else {
			
			// n-1 is not selected
			permutationNK(A, n - 1, k);

			// n-1 is selected
			A[n] = true;
			permutationNK(A, n - 1, k - 1);

			A[n] = false;
		}

	}

	public void generate() {
		permutationNK(new boolean[Pn + 1], Pn, Pk);
	}

	public static void main(String[] args) {
		PermutationNK permutationNK = new PermutationNK(4, 2); // Values of n and k.
		permutationNK.generate();

	}

}
