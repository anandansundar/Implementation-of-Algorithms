package imple;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class PriorityQueueSort1 {
	
	public <T> void Pqsort(ArrayList<T> array)
	{
		FirstTen(array);
		ArrayList<T> sorted = new ArrayList<>();
		Queue<T> pq = new PriorityQueue<T>(array);
	    for (int j = 0; j < array.size(); j++ )
	    {
	    sorted.add(j, pq.poll());
	    }
	    FirstTen(sorted);
	}
	
	 static <T> void FirstTen(ArrayList<T> input)
	 {
	    	
	    	int n = Math.min(input.size(), 10);
	        
	    	for(int i=0; i<n; i++)
	    	{
	            System.out.print(input.get(i) + " ");
	        }
	        
	    	System.out.println();
	    }
	
	public static void main(String[] args) {
	
		   ArrayList<Integer> array1= new ArrayList<>();
	
		   for (int i = 0; i < 10; i++)
		   	{
			   array1.add(i, new Random().nextInt(100));
		   	}
		   
		   PriorityQueueSort1 pqsort = new PriorityQueueSort1();
		   //long time1 = System.nanoTime();
		   pqsort.Pqsort(array1);	   
		   //long time2 = System.nanoTime();
		   //System.out.println("Time consumed : " + (time2 - time1)/1000000000.0);
	}
     
	}
