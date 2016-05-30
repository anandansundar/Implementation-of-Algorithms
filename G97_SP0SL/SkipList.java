import java.lang.Comparable;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Anandan Sundar, Ramesh Suthan Palani, Rahul Aravind Mehalingam, Sanjana Ramakrishnan
 *	
 *	Class which implements skip list functionalities
 */
public class SkipList<T extends Comparable<? super T>> implements Iterable<T>{

	public final int max_level = 4;

	/**
	 * Skip List Entry class.
	 *
	 */

	@SuppressWarnings("hiding")
	public class SLEntry <T>{

		T element;
		SLEntry<T> [] next;
		int level;

		@SuppressWarnings("unchecked")
		public SLEntry(T element, int level) {

			this.element = element;
			next = new SLEntry[level];
			this.level = level;

		}

	}

	/**
	 * Skip List Iterator class
	 *
	 */

	class SkipListIterator implements Iterator<T>{
		SLEntry<T> currEntry;
		public SkipListIterator() {
			currEntry=head;
		}
		@Override
		public boolean hasNext() {
			return currEntry.next[0].element != null;
		}

		@Override
		public T next() {
			currEntry=currEntry.next[0];
			return currEntry.element;
		}

	}

	SLEntry<T>  head, tail;
	int size;

	public SkipList() {

		head = new SLEntry<T>(null, max_level);
		tail = new SLEntry<T>(null, max_level);

		for (int i = max_level - 1; i >=0; --i){
			head.next[i] = tail;
			tail.next[i] = null;
		}
		size = 0;
	}

	boolean add(T x) {  // Add an element x to the list.  Returns true if x was a new element.

		if (contains(x)){
			return false;
		}

		else {

			int level = choice(x);
			//System.out.println("level of x: " + level);
			int size1 = level+1;

			SLEntry<T> [] prev = find(x);
			SLEntry<T> node = new SLEntry<T>(x, size1);

			for(int i=0; i<=level; ++i){
				node.next[i] = prev[i].next[i];
				prev[i].next[i] = node;
			}

			size++;
			return true;
		}
	}

	private int choice(T x) {

		int k = 0;
		while (k < max_level - 1){
			boolean b = new Random().nextBoolean();
			if (b){
				k++;
			}
			else{
				break;
			}
		}
		return k;
	}

	T ceiling(T x) { // Least element that is >= x, or null if no such element

		SLEntry<T> [] prev = find(x);
		SLEntry<T> ceil = prev[0].next[0];
		return ceil.element;

	}

	boolean contains(T x) {  // Is x in the list?

		SLEntry<T> [] prev = find(x); // Returns the node with the value or returns its closest predecessor

		if (prev[0].next[0].element == null){
			return false;
		}
		else if (prev[0].next[0].element.compareTo(x) == 0){
			return true;
		}
		else{
			return false;
		}
	}

	T findIndex(int index) {  // Return the element at a given position (index) in the list
		if (index >= size){
			System.out.println("The index is greater than the size of the skip list");
			return null;
		}

		else {
			SLEntry<T> node = head;
			int i;
			for (i = 0; i <= index; ++i){
				node = node.next[0];
			}
			return node.element;
		}
	}

	T first() {  // Return the first element of the list
		SLEntry<T> node = head.next[0];
		return node.element;
	}

	T floor(T x) {  // Greatest element that is <= x, or null if no such element
		SLEntry<T>[] node = find(x);
		T next_el = node[0].next[0].element;

		if (next_el.compareTo(x) == 0)
			return next_el;

		else return node[0].element;

	}

	boolean isEmpty() {  // Is the list empty?
		if (size == 0)
			return true;
		else
			return false;
	}


	T last() {  // Return the last element of the list
		SLEntry<T> node = head;
		while (node.next[0].element != null){
			node = node.next[0];
		}
		return node.element;
	}

	boolean remove(T x) {  // Remove x from list; returns false if x was not in list

		if (!contains(x)){
			System.out.println("The element is not in the list!");
			return false;
		}

		else {
			SLEntry<T> [] prev = find(x);
			SLEntry<T> node = prev[0].next[0];

			for (int i = 0; i < max_level - 1; ++i){

				if(prev[i].next[i]== node){
					prev[i].next[i] = node.next[i];
				}
				else
					break;
			}

			return true;
		}
	}
	int size() {  // Number of elements in the list
		return size;
	}

	//Function for finding the closest lesser element of the element.

	public SLEntry<T>[] find(T x){

		int nodeHeight = max_level - 1;
		SLEntry<T> p = head; 

		@SuppressWarnings("unchecked")
		SLEntry<T> [] prev = new SLEntry[max_level];

		for (int i = nodeHeight; i>=0; --i){

			while(p.next[i].element != null && p.next[i].element.compareTo(x) < 0){
				p = p.next[i];
			}

			prev[i] = p;
		}

		return prev;

	}

	public void printlist(){

		for(T elem : this){
			System.out.print(elem + " -> ");
		}

		System.out.println();
	}

	@Override
	public Iterator<T> iterator() {
		return new SkipListIterator();
	}

	public void options(){
		System.out.println("Enter the operation you would like to perform : ");
		System.out.println("1. Add");
		System.out.println("2. Remove");
		System.out.println("3. Get index");
		System.out.println("4. Floor");
		System.out.println("5. Ceil");
		System.out.println("6. Search");
		System.out.println("7. Get Size");
		System.out.println("8. Get First element in the skip list");
		System.out.println("9. Get last element in the skip list");
		System.out.println("10. Print skip list");
		System.out.println();
	}

	public static void main(String[] args) {

		SkipList<Integer> sl = new SkipList<>();
		sl.options();
		Scanner in = new Scanner(System.in);

		while(in.hasNext()) {

			Integer x = in.nextInt();

			switch(x){

			case 1: 
				System.out.println("Enter the number to add : ");
				Integer t = in.nextInt();
				System.out.print("Add " + t + " : ");
				sl.add(t);
				sl.printlist();
				sl.options();
				break;

			case 2: 
				System.out.println("Enter the number to remove : ");
				Integer r = in.nextInt();
				System.out.println("Remove " + r + " : ");
				sl.remove(r);
				sl.printlist();
				sl.options();
				break;

			case 3:	
				System.out.println("Enter the element's index in the skip list : " );
				Integer ind = in.nextInt();
				Integer a = sl.findIndex(ind);
				System.out.println("The index of " + ind + " in the skip list is : " + a);
				sl.options();
				break;

			case 4 : 
				System.out.println("Enter the element : ");
				Integer f1 = in.nextInt();
				Integer f = sl.floor(f1);
				System.out.println("The floor of " + f1 + " in the skip list is : " + f);
				sl.options();
				break;

			case 5 :
				System.out.println("Enter the element : ");
				Integer c1 = in.nextInt();
				Integer c = sl.ceiling(c1);
				System.out.println("The ceil of " + c1 + " in the skip list is : " + c);
				sl.options();
				break;

			case 6 :
				System.out.println("Enter the element to find : ");
				Integer find = in.nextInt();
				boolean b = sl.contains(find);
				if(b) System.out.println("The element is in the list");
				else System.out.println("The element is not in the list");
				sl.options();
				break;

			case 7 :
				System.out.println("The size of the skip list is " + sl.size);
				sl.options();
				break;

			case 8 :
				System.out.println("The first element in the list is : " + sl.first());
				sl.options();
				break;

			case 9 : 
				System.out.println("The last element in the list is : " + sl.last());
				sl.options();
				break;

			case 10: 
				sl.printlist();
				return;

			default:
				System.out.println("Enter a valid option");

			}

		}
		
		in.close();
	}



}