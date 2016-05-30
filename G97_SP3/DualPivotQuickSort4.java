package sp3;

import java.util.Random;

/**
 * @author Anandan
 *
 */

public class DualPivotQuickSort4 <T extends Comparable<? super T>>{

	public DualPivotQuickSort4(T [] input) {

		sort (input, 0, input.length-1);

	}

	private void sort(T[] input, int low, int high) {

		/*Two pivots x1 and x2 which are taken from the array and there are 3 regions which are formed. 
		Namely < x1, >= x1 and <= x2, > x2
		 */
		if (high <= low) return;

		T x1 = input[low];
		T x2 = input[high];

		if (x1.compareTo(x2)> 0){
			exchange(input, low, high);
			x1 = input[low];
			x2 = input[high];
		}

		int i = low + 1;
		int l = low + 1;
		int j = high - 1;

		while (i <= j){

			if (input[i].compareTo(x1) < 0){
				exchange(input, i, l);
				i++;
				l++;
			}

			else if (input[i].compareTo(x2) > 0 ){
				exchange(input, i, j);
				j--;
			}

			else{
				i++;
			}

		}

		exchange(input, low,  --l);
		exchange(input, high, ++j);

		//Recursion of three regions
		
		sort(input, low, l-1);
		sort (input, l+1, j-1);
		sort(input, j+1, high);

	}

	void exchange (T[] input, int left, int right){

		T swap = input[left];
		input[left] = input[right];
		input[right] = swap;

	}

	public static void main(String args[]){

		Integer [] myarray = new Integer[67108864];

		for (int i = 0; i < myarray.length; i++)
		{
			myarray[i] = new Random().nextInt(100000);
		}

		long time1 = System.currentTimeMillis();
		new DualPivotQuickSort4<>(myarray);
		long time2 = System.currentTimeMillis();

		System.out.println("Time consumed : " + (time2 - time1) + " milliseconds");

	}
}