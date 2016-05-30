import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Anandan Sundar, Ramesh Suthan Palani, Rahul Aravind Mehalingam, Sanjana Ramakrishnan
 *
 *	Class for comparing Skip List and Tree Set for contains, add and remove operations.
 */
public class TreeSetComp {

	public static void main(String[] args) {

		SortedSet<Integer> ts = new TreeSet<>();

		SkipList<Integer> sl = new SkipList<>();

		for (int i = 0; i < 20000; i++)
		{	
			int data = new Random().nextInt(10000); 
			ts.add(data);
			sl.add(data);
		}

		long time1 = System.nanoTime();
		ts.contains(1001);
		long time2 = System.nanoTime();
		long tsTime = time2 - time1;

		System.out.println("Tree Set time for contains operation : " + tsTime + " nano seconds");

		long time3 = System.nanoTime();
		sl.contains(1001);
		long time4 = System.nanoTime();
		long slTime = time4 - time3;

		System.out.println("Skip List time for contains operation : " + slTime + " nano seconds");
		
		time1 = System.nanoTime();
		ts.add(1001);
		time2 = System.nanoTime();
		tsTime = time2 - time1;
		
		System.out.println("Tree Set time for add operation : " + tsTime + " nano seconds");
		
		time3 = System.nanoTime();
		sl.add(1001);
		time4 = System.nanoTime();
		slTime = time4 - time3;
		
		System.out.println("Skip List time for add operation : " + slTime + " nano seconds");
		
		time1 = System.nanoTime();
		ts.remove(1001);
		time2 = System.nanoTime();
		tsTime = time2 - time1;
		
		System.out.println("Tree Set time for remove operation : " + tsTime + " nano seconds");
		
		time3 = System.nanoTime();
		sl.remove(1001);
		time4 = System.nanoTime();
		slTime = time4 - time3;
		
		System.out.println("Skip List time for remove operation : " + slTime + " nano seconds");
		
	}

}
