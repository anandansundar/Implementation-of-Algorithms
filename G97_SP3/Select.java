import java.util.PriorityQueue;

/**
 * To find K largest elements using partition and min heap methods and compare their performance.
 * @author Sanjana
 *
 */
public class Select {
	private static int phase = 0;
    private static long startTime, endTime, elapsedTime;
	public static void main(String[] args)
	{
		Integer[] A=new Integer[1000000];
		for(int i=0;i<1000000;i++)
		{
			A[i]=i;
		}
		int k=2;
		timer();
		int q=select(A,0,A.length-1,k);
		timer();
		System.out.println("K largest elements using partition algorithm are:");
		for(int i=q;i<=A.length-1;i++)
		{
			System.out.println(A[i]);
		}
		timer();
		PriorityQueue<Integer> queue=findKLargestUsingMinHeap(A,k);
		timer();
		System.out.println("K largest elements using min heap algorithm are:");
		while(!queue.isEmpty())
		{
			System.out.println(queue.poll());
		}
		
	}
	
	public static <T extends Comparable<? super T>> PriorityQueue<T> findKLargestUsingMinHeap(T[] A,int k)
	{
		PriorityQueue<T> queue=new PriorityQueue<T>(k);
		for(int i=0;i<k;i++)
		{
			
			queue.add(A[i]);
			
		}
		for(int i=k;i<A.length;i++)
		{
			if(A[i].compareTo(queue.peek())>0)
			{
				queue.remove();
				queue.add(A[i]);
			}
		}
			return queue;
	}
	
	public static <T extends Comparable<? super T>> int select(T[] A,int p,int r,int k)
	{
		int q;
		q=partition(A,p,r);
		if((r-q)>=k)
		{
			return select(A,q+1,r,k);
		}
		else if((r-q+1)==k)
		{
			return q;
		}
		else 
		{
			return select(A, p,q-1,k-(r-q+1));
		}
	
	}
	
	public static <T extends Comparable<? super T>> int partition(T[] A, int p, int r)
	{
		T x=A[r];
		int i=p-1;
		T temp;
		for(int j=p;j<=r-1;j++)
		{
			if(A[j].compareTo(x)<=0)
			{
				i=i+1;
				temp=A[i];
				A[i]=A[j];
				A[j]=temp;		
			}
		}
		temp=A[i+1];
		A[i+1]=A[r];
		A[r]=temp;
		return i+1;
	}
	public static void timer()
    {
        if(phase == 0) {
	    startTime = System.currentTimeMillis();
	    phase = 1;
	} else {
	    endTime = System.currentTimeMillis();
            elapsedTime = endTime-startTime;
            System.out.println("Time: " + elapsedTime + " msec.");
      //     memory();
            phase = 0;
        }
    }

}


