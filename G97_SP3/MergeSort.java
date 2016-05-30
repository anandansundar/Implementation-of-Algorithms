package sp3;

import java.util.*;

public class MergeSort<T extends Comparable<? super T>> {

	//
	
	public void Merge(T[] arr, int low, int mid, int high){
		int i = low, j = mid+1, k;
		int n = high-low+1;

		@SuppressWarnings("unchecked")
		T[] temp = (T[])new Comparable[n];

		for(k=0; k<n; k++){

			if(j > high) {
				temp[k] = arr[i];
				i++;
			}

			else if(i > mid || arr[i].compareTo(arr[j]) > 0){
				temp[k] = arr[j];
				j++;
			}

			else {
				temp[k] = arr[i];
				i++;
			}
		}

		for(k=0;k<n;k++){
			arr[low] = temp[k];
			low++;
		}
	}

	public void Sort(T[] arr, int low, int high){

		if(low < high){
			int mid = (low+high)/2;
			Sort(arr, low, mid);
			Sort(arr,  mid+1, high);
			Merge(arr, low, mid, high);

		}
	}

	public static void main(String[] args) {
		int n = 1048576; 
		Integer[] arr = new Integer[n];

		for(int i=0;i<n;i++){
			arr[i] = new Random().nextInt(100000);
		}

		//System.out.println(Arrays.toString(arr));

		MergeSort<Integer> ms = new MergeSort<>();

		long startReadTime = System.currentTimeMillis();    

		ms.Sort(arr, 0, n-1);

		//System.out.println(Arrays.toString(arr));

		long totalLoadTime = System.currentTimeMillis() - startReadTime;

		System.out.println(totalLoadTime + " milliseconds");
		
	}

}
