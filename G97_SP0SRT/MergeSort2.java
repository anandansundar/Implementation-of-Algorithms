package imple;

import java.util.Random;

public class MergeSort2<T> {
	 
	    public static <T extends Comparable<? super T>> void sort(T[] input)
	    {
	    	//System.out.println("Before Sorting " +input);
	    	long time1 = System.nanoTime();
	        input = mergeSort(input);
	        long time2 = System.nanoTime();
	        //System.out.println("After Sorting " + input);
	        System.out.println("Time consumed : " + (time2 - time1)/1000000000.0);
	    }
	 
	    @SuppressWarnings("unchecked")
		public static <T extends Comparable<? super T>> T[] mergeSort(T[] input)
	    { 
	        int center;
	        int size = input.length;
	        int subsize = size >> 1;
	        T [] left = (T[]) new Comparable[subsize + 1];
	        T [] right = (T[])new Comparable[subsize + 1];
	        if(input.length==1)    
	            return input;
	        else
	        {
	            center = size >> 1;
	           
	            for(int i=0; i<center; i++)
	            {
	                    left[i] = input[i];
	            }
	 
	            for(int j=0; j < (input.length - center); j++)
	            {
	                    right[j] = input[j+center];
	            }
	 
	            mergeSort(left);
	            mergeSort(right);
	            merge(left,right,input);
	 
	        }
	        return input;
	    }
	 
	    private static <T extends Comparable<? super T>> void merge(T[] left, T[] right, 
	            T[] whole) {
	 
	        int leftIndex = 0;
	        int rightIndex = 0;
	        
	        for (int wholeIndex = 0; wholeIndex < whole.length; wholeIndex++)
	        { 
	       
	            if ((rightIndex >= right.length) || (leftIndex < left.length && left[leftIndex].compareTo(right[rightIndex]) < 0)) 
	            {
	                whole[wholeIndex] = left[leftIndex];
	                leftIndex++;
	            }
	            else
	            {
	                whole[wholeIndex] = right[rightIndex];
	                rightIndex++;
	            }
	       
	        }
	 
	    }
	 
	    public static void main(String[] args) {
	        Integer [] input = new Integer[15];
	        for (int i = 0; i < 10; i++)
	    	input[i] = new Random().nextInt(100);        
	        sort(input);
	        
	    }
	 
	}
