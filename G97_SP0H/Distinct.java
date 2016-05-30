/**
 * 
 */


import java.util.Arrays;
import java.util.HashMap;

/**
 * Distinct - Class to find the distinct element in the array and move it to the front of the array
 * 
 * @author Anandan Sundar,Ramesh Suthan Palani,Rahul Aravind Mehalingam,Sanjana Ramakrishnan
 *
 */
public class Distinct <T> {

	
	
	/**
	 * Method to find distinct elements in the array of objects
	 * Given an array of unsorted objects of some class (that implements hashCode and equals), 
	 * move the distinct elements of the array to the front. The function behaves as follows. 
	 * Let k be the number of distinct elements of A (k is not known). 
	 * Find the k distinct elements of arr[], and move them to arr[0..k-1]. Return k.
	 * 
	 * @param arr - array of unsorted objects
	 * @return
	 */
	public static <T> int findDistinct(T[] arr){

		HashMap<T, Integer> hash = new HashMap<>();

		Integer count; //Stores the number of occurrences of each value in the array
		Integer occurence;//No of occurrences of the particular key when retrived
		T val, swap;

		//Adding Values to the hashmap with element as key and no of occurences as count
		
		for (T t : arr){
			if (hash.containsKey(t)){
				count = hash.get(t);
				hash.replace(t, count + 1);
			}
			else
				hash.put(t, 1);	
		}

		int i, k = 0;		

		// Loop for segregating distinct and non-distinct elements in the array
		
		for (i = 0; i < arr.length; i++) {

			val = arr[i];
			occurence = hash.get(val);

			if (occurence == 1){

				swap = arr[k];
				arr[k] = arr[i];
				arr[i] = swap;
				k++;

			}

		}

		return k;

	}

	public static void main(String[] args) {

		Integer[] array = {3, 5, 1, 1, 7, 5, 4, 4, 10, 21};
		System.out.println("Given array " +Arrays.toString(array));

		int k = findDistinct(array);

		System.out.println("Number of distinct elements in the array is " +k);

		System.out.println("Array after moving the elements to the left " + Arrays.toString(array));

	}

}
