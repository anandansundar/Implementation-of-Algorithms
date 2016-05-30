/**
 * 
 */


import java.util.Arrays;
import java.util.HashMap;

/**
 * Frequent - Class contains method to find the most frequent element
 * 
 * @author Anandan Sundar,Ramesh Suthan Palani,Rahul Aravind Mehalingam,Sanjana Ramakrishnan
 */

public class Frequent {	
	
	/**
	 * Method to find an integer that is most frequent in the array
	 * @param arr : input array of elements
	 */
	
	public static int mostFrequent(int[] arr){

		HashMap<Integer, Integer> hash = new HashMap<>();

		Integer count, val;

		// Loading the values and no of occurences to the hashmap
		
		for (int t : arr){
				
			if (hash.containsKey(t)){
				count = hash.get(t);
				hash.replace(t, count + 1);
			}
	
			else
				hash.put(t, 1);	
		
		}

		int i, f = 0, element = 0;
		
 // Loop for finding the element which is most frequent
		
		for (i = 0; i < arr.length; i++) {

			val = arr[i];

			if (f < hash.get(val)){
				f = hash.get(val);
				element = val;
			}
			
		}

		return element;

	}

	public static void main(String[] args) {

		int[] array = {3, 5, 1, 1, 7, 5, 4, 4, 10, 21, 4, 10, 10, 10};
		System.out.println("Given array " +Arrays.toString(array));

		int k = mostFrequent(array);

		System.out.println("The most frequent element in the array is " +k);

	}

}
