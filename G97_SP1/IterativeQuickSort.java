import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class IterativeQuickSort {
	
	/****
	 * Partition function logic
	 * 
	 * 1) Last element is taken as the pivot.
	 * 2) The elements that less than the pivot are placed to the left of the pivot and those that are greater are placed to the right of the pivot.
	 * 
	 * @param arr
	 * @param low
	 * @param high
	 * @return
	 */
	public static int partition(List<Integer> arr, int low, int high) {
		

		int pivot = arr.get(high);
		int idx = low - 1;
		
		for(int i = low; i < high; i++) {
			
			//Elements that are less than the pivot are placed to the left side of pivot
			if(arr.get(i) <= pivot) {
				idx++;
				
				//swapping
				Integer temp = arr.get(idx);
				arr.set(idx, arr.get(i));
				arr.set(i, temp);
			}
		}
		
		//The pivot is placed to the right position.
		Integer temp = arr.get(idx + 1);
		arr.set(idx + 1, arr.get(high));
		arr.set(high, temp);
		
		return idx + 1;
	}
	
	public static void quicksort(List<Integer> arr) {
		
		if(arr == null || arr.isEmpty()) return;
		
		int low = 0;
		int high = arr.size() - 1;
		
		//stack creation
		Stack<Integer> stack = new Stack<Integer>();
		
		//The values of low and high are pushed onto the stack
		stack.push(low);
		stack.push(high);
		
		//pop until stack is empty
		while( stack.isEmpty() == false) {
			high = stack.pop();
			low = stack.pop();
			
			int pivot = partition(arr, low, high);
			
			//Elements on left side of pivot need to be processed
			if(pivot - 1 > low) {
				stack.push(low);
				stack.push(pivot - 1);
			}
			
			//Elements on right side of pivot need to be processed
			if(pivot + 1 < high) {
				stack.push(pivot + 1);
				stack.push(high);
			}
			
		}
	}
	
	public static void main(String args[]) {
		List<Integer> arr = new ArrayList<Integer>();
		Scanner s = new Scanner(System.in);
		
		System.out.println("Enter the array size");
		int arr_size = s.nextInt();
		
		System.out.println("Enter " + arr_size + " numbers");
		for(int i = 0; i < arr_size; i++) {
			int val = s.nextInt();
			arr.add(val);
		}
		
		
		
		System.out.println("Elements before sorting.....");
		System.out.println(arr);
		
		quicksort(arr);
		
		System.out.println("Elements after sorting");
		System.out.println(arr);
	}

}
