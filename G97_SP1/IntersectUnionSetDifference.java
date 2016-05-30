package Assignment1;
import java.util.List;
import java.util.ListIterator;

public class IntersectUnionSetDifference {

	public static<T extends Comparable<? super T>>
    void intersect(List<T> l1, List<T> l2, List<T> outList) {
	try { 
	//List Iterators for iterating l1 and l2
	ListIterator<T> itrA = l1.listIterator();
	ListIterator<T> itrB = l2.listIterator();
	int asize = l1.size();
	int bsize = l2.size();
	if (asize > 0 && bsize > 0){
	
	int i = 0; 
	int j = 0;
	T Avalue = itrA.next();
	T Bvalue = itrB.next();
	// If Avalue = Bvalue we add to the intersect list, else we increment the lesser value's iterator
	while (i < asize && j < bsize ){
		if (Avalue == Bvalue)
    	{
    	    	outList.add(Avalue);
    	    	if (itrA.hasNext() && itrB.hasNext()){
    	     	Avalue = itrA.next();
    	     	Bvalue = itrB.next();
    	    	}
    	    	i++; j++;
        }
    	     
    	else if (Avalue.compareTo(Bvalue) < 0 && itrA.hasNext()){
    	     	Avalue = itrA.next();
    	     	i++;
    	     }
    		
    	else {
    		if (itrB.hasNext()){
    			Bvalue = itrB.next();
    		}
    		j++;
    		}
    		
    	}
    	
	}
	// for small inputs, the below commented code can be uncommented
	
	/*if (outList.size() > 0)
	System.out.println("Intersection is: " + outList);
	else
	System.out.println("No elements in intersected set");*/
	}
	catch (NullPointerException ne){
		
		System.out.println("One or both the arrays are empty" + ne);
	}


	}
	
   // Return elements common to l1 and l2, in sorted order.
   // outList is an empty list created by the calling
   // program and passed as a parameter.
   // Function should be efficient whether the List is
   // implemented using ArrayList or LinkedList.
   
public static<T extends Comparable<? super T>>
    void union(List<T> l1, List<T> l2, List<T> outList) {
	//List Iterators for iterating l1 and l2
	try { 
		ListIterator<T> itrA = l1.listIterator();
		ListIterator<T> itrB = l2.listIterator();
		int asize = l1.size();
		int bsize = l2.size();
		if (asize > 0 && bsize > 0){
		int i = 0; 
		int j = 0;
		T Avalue = itrA.next();
		T Bvalue = itrB.next();
		// We are including all values in the union list. 
		// If the value is occurring on both the lists, we are adding value in any one in the list
		while(i < asize && j < bsize)
		{
			if (Avalue == Bvalue)
	    	{
	    	     	if (itrA.hasNext() && itrB.hasNext()){
	    	    	outList.add(Avalue);
	    	    	Avalue = itrA.next();
	    	     	Bvalue = itrB.next();
	    	     	i++;
	    	     	j++;
	    	    	}
	    	    	else if (itrA.hasNext())
	    	    	{
	    	    		outList.add(Avalue);
	    	    		Avalue = itrA.next();
	    	    		i++;
	    	    		j++;
	    	    	}
	    	    	else if (itrB.hasNext())
	    	    	{
	    	    		outList.add(Bvalue);
	    	    		Bvalue = itrB.next();
	    	    		i++;
	    	    		j++;
	    	    	}
	    	    	else {
	    	    		outList.add(Avalue);
	    	    		i++;
	    	    		j++;
	    	    	}
	    	    	  	
	        }
	    	     
	    	else if (Avalue.compareTo(Bvalue) < 0)
	    	{
	    	    outList.add(Avalue);
	    	    if (itrA.hasNext()){
	    		Avalue = itrA.next();
	    	    }
	    	    i++;
	    	 }
	    		
	    	else {
	    		outList.add(Bvalue);
	    		if (itrB.hasNext()){
	    		Bvalue = itrB.next();
	    		}
	    		j++;
	    	}
	    	
		}
		//if list B is full, copy the remaining elements in list A to the output.
		while(i < asize){
			outList.add(Avalue);
			if (itrA.hasNext())
			Avalue = itrA.next();
			i++;
		}
		// If list A is full, copy the remaining elements in list B to the output.
		while(j < bsize){
			outList.add(Bvalue);
			if (itrB.hasNext())
			Bvalue = itrB.next();
			j++;
		}
		
			//System.out.println("Union is: " + outList);
			}
		}
		catch (NullPointerException ne){
			
			System.out.println("One or both the arrays are empty" + ne);
		}
		}
	
   // Return the union of l1 and l2, in sorted order.
   // Output is a set, so it should have no duplicates.

public static<T extends Comparable<? super T>>
    void difference(List<T> l1, List<T> l2, List<T> outList) {
	
	try { 
		ListIterator<T> itrA = l1.listIterator();
		ListIterator<T> itrB = l2.listIterator();
		int asize = l1.size();
		int bsize = l2.size();
		if (asize > 0 && bsize > 0){
		int i = 0; 
		int j = 0;
		T Avalue = itrA.next();
		T Bvalue = itrB.next();
		// We are including all values of set A which doesn't occur in set B. 
		while(i <= asize && j <=bsize){
			// This algorithm will work for sets even if their sizes are not equal.
			// Incrementing the Avalue and Bvalue if they occur in both the lists.
			if (Avalue == Bvalue)
	    	{
	    	    	if (itrA.hasNext() && itrB.hasNext()){
	    	     	Avalue = itrA.next();
	    	     	Bvalue = itrB.next();
	    	    	}
	    	    	i++;
	    	     	j++;
	        }
	    	//If Avalue is lesser than Bvalue, we are adding Avalue to the set.
	    	else if (Avalue.compareTo(Bvalue) < 0)
	    	{    	
	    			if (itrA.hasNext()){
	    			outList.add(Avalue);
	    	    	Avalue = itrA.next();
	    	    	i++;
	    			}
	    			else 
	    			break;
	    	}
	    	//If Bvalue is lesser, we are moving the iterator
	    	else {
	    			if (itrB.hasNext()){
	    			Bvalue = itrB.next();
	    			}	
	    			j++;
	    		}
	    		
	    	}
			
			T Avalue_prev = null;
			
			while(i <= asize){
            //This is very important and used for boundary conditions,
			//Adding the remaining of Avalues if itrB has done with the iteration.
			if (itrA.hasNext())
			{	
					Avalue = itrA.next();
					Avalue_prev = Avalue;
					outList.add(Avalue);
					i++;
			}
			else 
				{	
					if (Avalue != Avalue_prev)
					outList.add(Avalue);
					i = i + 2;
				 }

			}
	
	}
			
		//System.out.println("Set Difference (A - B) is: " + outList);
		
	}
	
		catch (NullPointerException ne){
			
		System.out.println("One or both the arrays are empty" + ne);
		
		}
   // Return l1 - l2 (i.e, items in l1 that are not in l2), in sorted order.
   // Output is a set, so it should have no duplicates.
}

}
