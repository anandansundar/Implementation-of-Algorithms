/**
 * @author Anandan Sundar, Ramesh Suthan Palani, Rahul Aravind Mehalingam,
 *         Sanjana Ramakrishnan
 *
 */
public class Combination {

	public Combination(Boolean[] A) {
		for (int i = 0; i < A.length; ++i) {
			A[i] = false;
		}
	}

	Integer count = 0;

	private void visit(Boolean[] A) {

		for (int i = 0; i < A.length; ++i) {
			if (A[i]) {
				System.out.print(i);
			}
		}
		System.out.println();
		count++;
	}

	private void combine(Boolean[] A, Integer n, Integer k) {

		if (k > n) {
			return;
		}

		else if (k == 0) {
			visit(A);
		}

		else {

			// if n is not getting selected
			combine(A, n - 1, k);

			// if n is getting selected
			A[n] = true;
			combine(A, n - 1, k - 1);

			A[n] = false; // clean up

		}
	}

	public static void main(String[] args) {

		Boolean[] A = new Boolean[5];
		Combination comb = new Combination(A);
		comb.combine(A, A.length - 1, 3);
		System.out.println("Total number of combinations : " + comb.count);
	}

}
