import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class BigIntegers {
	
	public static void add(List<Integer> x, List<Integer> y, List<Integer> z, int b) {
		
		// If both x and y are null, then return nothing
		if(x == null && y == null) return;
		
		// If x is null and y is not null, then return y which is the result
		if(x == null || x.isEmpty()) z.addAll(y);
		
		// If y is null and x is not null, then x is the result
		if(y == null || y.isEmpty()) z.addAll(x);
		
		//Fetching iterators
		ListIterator<Integer> x_iter = x.listIterator();
		ListIterator<Integer> y_iter = y.listIterator();
		
		Integer carry = 0;
		
		// Adding the numbers till either x or y gets empty 
		while(x_iter.hasNext() && y_iter.hasNext()) {
			Integer val = x_iter.next() + y_iter.next() + carry;
			carry = val / b;
			val = val % b;
			z.add(val);
		}
		
		//If there are remaining elements in x, they are added to the result and carry is kept track of
		while(x_iter.hasNext()) {
			Integer val = x_iter.next() + carry;
			carry = val / b;
			val = val % b;
			z.add(val);
		}
		
		// If there are remaining elements in y, they are added to the result and carry is kept track of
		while(y_iter.hasNext()) {
			Integer val = y_iter.next() + carry;
			carry = val / b;
			val = val % b;
			z.add(val);
		}
		
		//If carry exists, its added to the result.
		if(carry > 0) z.add(carry);
	}
	
	
	/*
	 * Algorithm for subtraction of base b numbers
	 * 
	 * The question statement mentions that x will always be greater than y
	 * 
	 * Subtraction of y from x is carried out by computing b's complement for y and that is added with x. The overflow bit is ignored.
	 * 
	 * Logic followed for b's complement of y.
	 * 
	 * 1) The elements are retained till a non-zero number is encountered.
	 * 2) Once a non-zero number is encountered, it is subtracted with base b.
	 * 3) The remaining elements are subtracted accordingly with (b - 1).
	 * 
	 */
	public static void subtract(List<Integer> x, List<Integer> y, List<Integer> z, int b) {
		
		// If both x and y are null, return nothing
		if(x == null && y == null) return;
		
		// if x is not null and y is empty, then return x which is the result
		if(y == null || y.isEmpty()) z.addAll(x);
		
		//Fetching the iterators
		ListIterator<Integer> x_iter = x.listIterator();
		ListIterator<Integer> y_iter = y.listIterator();
		
		Integer carry = 0;
		Integer temp_x = 0;
		Integer temp_y = 0;
		
		//Track the first occurence of non-zero
		boolean chkNonZero = false;
		
		//track the current position of the list on every iteration
		int idx = 0;
		
		//track the most significant non-zero index
		int msbIdx = 0;
		
		//Loop through the elements of the array.
		while(x_iter.hasNext()) {
			temp_x = x_iter.next();
			
			//If the y list size is less than x list, then zeroes are padded.
			temp_y = y_iter.hasNext() ? y_iter.next() : 0;
			
			if(!chkNonZero) {
				if(temp_y != 0) {
					chkNonZero = true;
					temp_y = b - temp_y;
				}
			}
			else temp_y = (b - 1) - temp_y;
			
			Integer val = temp_x + temp_y + carry;
			carry = val / b;
			val = val % b;
			z.add(val);
			if(val != 0) {
				msbIdx = idx;
			}
			idx++;
		}
		z.subList(msbIdx + 1, z.size()).clear();
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		BigIntegers bi = new BigIntegers();
		
		System.out.println("Enter the base");
		int base = s.nextInt();
		
		List<Integer> l1 = new LinkedList<Integer>();
		
		System.out.println("Enter the size of list x");
		int x_size = s.nextInt();
		
		System.out.println("Enter " + x_size + " numbers");
		for(int i = 0; i < x_size; i++) {
			int val = s.nextInt();
			l1.add(val);
		}

		
		List<Integer> l2 = new LinkedList<Integer>();
		
		System.out.println("Enter the size of list y");
		int y_size = s.nextInt();
		
		System.out.println("Enter " + y_size + " numbers");
		for(int i = 0; i < y_size; i++) {
			int val = s.nextInt();
			l2.add(val);
		}
		
		
		//addition of two numbers of base b
		
		List<Integer> add_res = new LinkedList<Integer>();
		add(l1, l2, add_res, base);
		
		//subtraction of two numbers of base b
		
		List<Integer> sub_res = new LinkedList<Integer>();
		subtract(l1, l2, sub_res, base);
		
		System.out.println("Result after addition " + add_res);
		System.out.println("Result after subtraction " +sub_res);
	}

}
