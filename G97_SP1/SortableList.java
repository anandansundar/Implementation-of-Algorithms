
public class SortableList<T extends Comparable<? super T>> extends SinglyLinkedList<T> {
    public SortableList<T> merge(SortableList<T> lst2) {
    	SortableList<T>.Entry<T> p1=this.header.next;
    	SortableList<T>.Entry<T> p2=lst2.header.next;
    	SortableList<T> lst=new SortableList<T>();
    	SortableList<T>.Entry<T> p=lst.header;
    	System.out.println("merging");
		while(p1!=null && p2!=null)
		{
			if(p1.element.compareTo(p2.element)<0)
			{
				p.next=p1;
				p1=p1.next;
			}
			else
			{
				p.next=p2;
				p2=p2.next;
			}
			p=p.next;
		}
		if(p1!=null)
		{
			p.next=p1;
		}
		if(p2!=null)
		{
			p.next=p2;
		}
		return lst;
    }

     public <T extends Comparable<? super T>>SortableList<T> mergeSort(SortableList<T> lst1) {
    	int length=lst1.length();
    	System.out.println("length ="+length);
    	SortableList<T> lst=null;
    	if(length>1)
    	{
    	int mid=length/2;
    	int count=0;
    	SortableList<T>.Entry<T> p=lst1.header;
    	SortableList<T> lst2=new SortableList<T>();
    	while(count<mid)
    	{
    		
    		p=p.next;
    		count++;
    	}
    	lst2.header.next=p.next;
    	p.next=null;
    	
    	lst1=mergeSort(lst1);
    	lst2=mergeSort(lst2);
    	System.out.println("printing list 1");
    	lst1.printList();
    	System.out.println("printing list 2");
    	lst2.printList();
    	lst=lst1.merge(lst2);
    	System.out.println("after merging");
    	lst.printList();
    	
    	}
    	else
    	{
    		lst=lst1;
    	}
    	return lst;
    }

    /*public static<T extends Comparable<? super T>> void mergeSort(SortableList<T> lst1) {
    	
    	lst1.mergeSort();
    	
    	
    }
    */

    public static void main(String[] args) {
    	SortableList<Integer> lst1 = new SortableList<>();
	    lst1.add(1);
	    lst1.add(7);
	    lst1.add(3);
	    lst1.add(2);
	    lst1.add(20);
	    lst1.add(12);
	    lst1.add(18);
	    lst1.add(4);
	    lst1.add(25);
	    lst1.add(23);
	    lst1.add(27);
	    lst1.printList();
	    lst1.mergeSort(lst1);
	    lst1.printList();
    }
}
