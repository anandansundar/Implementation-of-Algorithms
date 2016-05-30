package sp3;

import java.util.Random;

public class QuickSort2 <T extends Comparable<? super T>> {

	public QuickSort2(T[] input){
		int low = 0;
		int high = input.length - 1;
		//System.out.println(Arrays.toString(input));
		quickSort(input, low, high);
		//System.out.println(Arrays.toString(input));
	}
	
	//Quicksort with one pivot
	
	public void quickSort( T[] input, int low, int high) {

		if (input == null || input.length == 0)
			return;

		if (low >= high)
			return;

		//Partitioning based on pivot
		
		T pivot = input[low + (high-low)/2];

		int i = low, j = high;
		while (i <= j) {
			while (input[i].compareTo(pivot) < 0) {
				i++;
			}

			while (input[j].compareTo(pivot) > 0) {
				j--;
			}

			if (i <= j) {
				T temp = input[i];
				input[i] = input[j];
				input[j] = temp;
				i++;
				j--;
			}
		}

		if (low < j)
			quickSort(input, low, j);

		if (high > i)
			quickSort(input, i, high);
	
	}

	public static void main(String[] args) {

		Integer[] myarray = new Integer[1048576];
		
		for (int i = 0; i < myarray.length; i++)
	   	{
		   myarray[i] = new Random().nextInt(100000);
	   	}
		long startReadTime = System.currentTimeMillis();
		
		new QuickSort2<>(myarray);
		
		long totalLoadTime = System.currentTimeMillis() - startReadTime;

		System.out.println(totalLoadTime + " milliseconds");
	
	}
}
