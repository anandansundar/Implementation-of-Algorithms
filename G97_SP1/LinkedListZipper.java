package Assignment1;

import java.util.*;

public class LinkedListZipper<T> {
    public class Entry<T> {
        T element;
        Entry<T> next;

        public Entry() {
			// TODO Auto-generated constructor stub
		}
        
        Entry(T x, Entry<T> next) {
            element = x;
            this.next = next;
        }
    }

    Entry<T> header, tail;
    int size;

    LinkedListZipper() {
        header = new Entry<>(null, null);
        tail = null;
        size = 0;
    }

    void add(T x) {
        if(tail == null) {
            header = new Entry<>(x, header.next);
            tail = header;
            
        } 
        
        else {
            tail.next = new Entry<>(x, null);
            tail = tail.next;
        }
	size++;
    }

    void printList() {
        Entry<T> x = header;
        while(x != null) {
            System.out.print(x.element + " ");
            x = x.next;
        }
        System.out.println();
    }

    void multiUnzip(int k) {
    	//If the given list is of size k or less than k just return
    	if (size <= k){ 
    		return;
    	}
    	//Creating an array of head and tail pointers of k size
    	ArrayList<Entry<T>> headptr = new ArrayList<>(k);
    	ArrayList<Entry<T>> tailptr = new ArrayList<>(k);
    	
    	Entry<T> temp = header;
    	int i, j;
    	// Set head and tail pointers in positions 0 through k -1
    	for (i = 0; i < k && temp != null; i++){
    		headptr.add(i, temp);
    		tailptr.add(i, temp);
    		temp = temp.next;
    	}
    	
    	Entry<T> c = temp;
 
    	int counter = 0;
    	//Linking the tail(counter) node k+1th node and moving the tail pointers
    	while (c != null)
    	{
    	
    		tailptr.get(counter).next = c;
    		tailptr.set(counter, c);
    		c = c.next;
    		counter++;
    		counter = counter % k;
    		
    	}
    	//Linking the unconnected nodes to form a complete list
    	for (j = 0; j < tailptr.size() - 1; j++)
    	{
    	tailptr.get(j).next = headptr.get(j+1);
    	}
    	tailptr.get(j).next = null;
    	
       }
    
    void unzip() {
	if(size < 3) {  // Too few elements.  No change.
	    return;
	}

	Entry<T> tail0 = header;
	Entry<T> head1 = tail0.next;
	Entry<T> tail1 = head1;
	Entry<T> c = tail1.next;
	int state = 0;

	// Invariant: tail0 is the tail of the chain of elements with even index.
	// tail1 is the tail of odd index chain.
	// c is current element to be processed.
	// state indicates the state of the finite state machine
	// state = i indicates that the current element is added after tail (i=0,1).
	while(c != null) {
	    
		if(state == 0) {
		tail0.next = c;
		tail0 = c;
		c = c.next;
	    } 
		
		else {
		tail1.next = c;
		tail1 = c;
		c = c.next;
	    }
	    state = 1 - state;
	}
	tail0.next = head1;
	tail1.next = null;
    }

    public static void main(String[] args) {
        int n = 10;
        if(args.length > 0) 
        {
            n = Integer.parseInt(args[0]);
        }

        LinkedListZipper<Integer> lst = new LinkedListZipper<>();
        for(int i=1; i<=n; i++) {
            lst.add(new Integer(i));
        }
        lst.printList();
        lst.multiUnzip(4);
        lst.printList();
    }
}