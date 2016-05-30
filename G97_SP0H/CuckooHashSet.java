import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * 
 * CuckooHashSet - Hashed Set using Cuckoo Hashing algorithm  
 * 
 * @author Ramesh Suthan Palani,Rahul Aravind Mehalingam,Sanjana Ramakrishnan,Anandan Sundar
 *
 * @param <T> : T must implement equals and hashCode method to work properly
 *   
 */
public class CuckooHashSet<T> {
	public final static int initTableSize = 3; // initial Table Size
	int currSize; // current size of the table
	int maxRetry = 5; // max retry for reinsert operation before resizing the
						// table
	float resizeFactor = 2;// resize factor
	int noOfHashFn = 2; // number of hash functions
	HashingFunction hashFunctions[]; //array of hash functions
	T table[][]; // two dimensional table (No of Hashing function * currSize)

	
	/**
	 * Constructor for the CuckooHashSet
	 * @param hashFunctions - Array of hash functions to use in the cuckoo Hashing 
	 */
	@SuppressWarnings("unchecked")
	public CuckooHashSet(HashingFunction hashFunctions[]) {
		this.noOfHashFn = hashFunctions.length;
		this.hashFunctions = hashFunctions;
		this.table = (T[][]) new Object[noOfHashFn][initTableSize];
		currSize = initTableSize;
	}

	
	/**
	 * Default constructor uses 2 hashing functions(RotationalHashFunction,BernsteinHashFunction)
	 */
	@SuppressWarnings("unchecked")
	public CuckooHashSet() {
		currSize = initTableSize;
		table = (T[][]) new Object[noOfHashFn][initTableSize];
		hashFunctions = new HashingFunction[noOfHashFn];
		hashFunctions[0] = new RotationalHashFunction();
		hashFunctions[1] = new BernsteinHashFunction();
	}


	/**
	 * Method to insert the object into the set if it is not present already.
	 * 
	 * @param val
	 * @return - true on success
	 */
	public boolean insert(T val) {
		if (find(val) != null) {
			//System.out.println("Insertion failed. Reason->Duplicate value:"+ val);
			return false;
		}

		int key;

		for (int i = 0; i < noOfHashFn; i++) {
			HashingFunction hashingFunction = this.hashFunctions[i];
			key = hashingFunction.getHash(val, currSize);
			if (table[i][key] == null) {
				table[i][key] = val;
				return true;
			}

		}

		// inserting into first slot and kicking out the current element
		key = hashFunctions[0].getHash(val, currSize);
		T prevVal = table[0][key];
		table[0][key] = val;

		//System.out.println("Slot full: " + val + " kicks out " + prevVal);
		// reinsert the kicked out value using hash function 2 with retry
		// counter set to 1
		reinsert(prevVal, 1, 1);

		return true;
	}

	/**
	 * Method to Reinsert the object which are kicked out during insertion
	 * 
	 * @param val
	 *            - object to be inserted
	 * @param i
	 *            - index of the hashing function to use
	 * @param retry
	 *            - number of retry allowed *
	 */
	public void reinsert(T val, int i, int retry) {
		int key;
		//System.out.println("Reinsert: " + val + " Retry counter: " + retry);

		key = hashFunctions[i].getHash(val, currSize);

		if (table[i][key] == null) {
			table[i][key] = val;
			return;
		}

		// if retry reaches the max retry limit,resize the table and insert the
		// value
		if (retry + 1 > maxRetry) {
			// increase the size of the table and try to insert
			resizeTable();
			//System.out.println("Inserting: " + val);
			insert(val);
			return;
		}

		// choose the next function to use
		int j = i + 1;
		if (j > noOfHashFn - 1) {
			j = 0;
		}

		T prevVal = null;
		prevVal = table[i][key];
		table[i][key] = val;

		//System.out.println("Clashing at reinsert:" + prevVal + "<-->" + val);
		reinsert(prevVal, j, retry + 1);

	}

	/**
	 * Method to resize the table when there is a overflow
	 */
	@SuppressWarnings("unchecked")
	public void resizeTable() {

		T oldtable[][] = table;
		currSize = (int) resizeFactor * currSize;
		//System.out.println("Increasing the table to size:" + currSize);
		table = (T[][]) new Object[noOfHashFn][currSize];

		for (int i = 0; i < noOfHashFn; i++) {
			for (Object o : oldtable[i]) {
				if (o != null) {
					T val = (T) o;
					insert(val);
				}

			}
		}

	}

	/**
	 * Method to find whether the object is present in the set or not
	 * 
	 * @param val
	 * @return - Object if present,else return null
	 */
	public T find(T val) {
		int key;

		for (int i = 0; i < noOfHashFn; i++) {

			key = hashFunctions[0].getHash(val, currSize);
			T mappedVal = table[i][key];

			if (mappedVal != null && val.equals(mappedVal)) {
				//System.out.println("Found: " + val);
				return val;
			}
		}
		// //System.out.println(val + " Not found");
		return null;
	}

	/**
	 * Method to delete the entry from the set
	 * 
	 * @param val
	 * @return - Object on successful deletion,else return null
	 */
	public T Delete(T val) {

		int key;
		for (int i = 0; i < noOfHashFn; i++) {

			key = hashFunctions[0].getHash(val, currSize);
			T mappedVal = table[i][key];
			if (mappedVal != null && val.equals(mappedVal)) {
				//System.out.println("Deleting: " + val);
				table[i][key] = null;
				return val;
			}
		}

		//System.out.println(val + " not found");
		return null;
	}

	/**
	 * Method to print the values in the table
	 */
	public void printTable() {
		for (int i = 0; i < noOfHashFn; i++) {
			//System.out.println("-----------------------------");
			//System.out.println("         Table" + i);
			//System.out.println("-----------------------------");
			for (Object o : table[i]) {
				System.out.println(o);
			}
		}
	}

	public static void main(String[] args) {
		
		//creating instances of 2 hash functions(Rotational Hashing and Bernstein hashing)
		HashingFunction[] hashFunctions = new HashingFunction[2];
		hashFunctions[0] = new RotationalHashFunction();
		hashFunctions[1] = new BernsteinHashFunction();

		CuckooHashSet<Integer> cuckooHashing = new CuckooHashSet<Integer>(hashFunctions);

		ArrayList<Integer> numList = new ArrayList<>();

		for (int i = 0; i < 1000000; i++) {
			numList.add(i);
		}

		Collections.shuffle(numList);
		HashSet<Integer> hashSet = new HashSet<Integer>();

		long startTime = System.currentTimeMillis();
		for (int i : numList) {
			//System.out.println(i);
			hashSet.add(i);
		}

		long endTime = System.currentTimeMillis();
		System.out.println("Total Time taken for Hash Set(ms):"
				+ (endTime - startTime));

		startTime = System.currentTimeMillis();
		for (int i : numList) {
			cuckooHashing.insert(i);
		}

		endTime = System.currentTimeMillis();
		System.out.println("Total Time taken for cuckooHashing(ms): "
				+ (endTime - startTime) + " with max retry: "
				+ cuckooHashing.maxRetry);
		System.out.println("CuckooHashing Table size:"+cuckooHashing.currSize * cuckooHashing.noOfHashFn);
		//System.out.println(cuckooHashing.find(numList.size()-1));

		/*
		 * 
		 * for (int i = 0; i < 35; i++) {
		 * 
		 * //System.out.println("Inserting:" + i); cuckooHashing.insert(i);
		 * //System.out .println(
		 * "----------------------------------------------------------------------------------"
		 * ); // cuckooHashing.insert(nRandom.n); }
		 * 
		 * //System.out.println("Final Table size" + cuckooHashing.currSize);
		 * 
		 * 
		 * cuckooHashing.printTable();
		 * 
		 * for (int i = 0; i < 39; i++) { cuckooHashing.find(i); }
		 * 
		 * cuckooHashing.Delete(2);
		 * 
		 * cuckooHashing.find(2); cuckooHashing.insert(1);
		 */
	}

}


/*
Sample Output:

Total Time taken for Hash Set(ms):1007
Total Time taken for cuckooHashing(ms): 1831 with max rety: 5
CuckooHashing Table size:12582912

*/