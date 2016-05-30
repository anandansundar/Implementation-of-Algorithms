package Assignment1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class IUSDriver {

	public static void main(String[] args) {
		SortedSet<Integer> ss1= new TreeSet<>();
		SortedSet<Integer> ss2= new TreeSet<>();
		for (int i = 0; i < 1000000; i++)
		{
			ss1.add(new Random().nextInt(1000000));
			ss2.add(new Random().nextInt(1000000));
		}
		
		List<Integer> loutintersect= new ArrayList<>();
		List<Integer> loutunion = new ArrayList<>();
		List<Integer> loutsetdiff = new ArrayList<>();
		
		List<Integer> l1 = new ArrayList<>(ss1);
		List<Integer> l2 = new ArrayList<>(ss2);
		//Uncomment below when using small sets
		//System.out.println("Set 1 : " +l1);
		//System.out.println("Set 2 : " +l2);
		
		long time1 = System.nanoTime();
		IntersectUnionSetDifference.intersect(l1, l2, loutintersect);
		IntersectUnionSetDifference.union(l1, l2, loutunion);
		IntersectUnionSetDifference.difference(l1, l2, loutsetdiff);
		long time2 = System.nanoTime();
		
		System.out.println("Time consumed : " + (time2 - time1)/1000000000.0 + "seconds");
	}

}
