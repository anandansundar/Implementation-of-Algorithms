

public class SinglyLinkedList1<T> {
    public class Entry<T> {
        T element;
        Entry<T> next;

        Entry(T x, Entry<T> nxt) {
            element = x;
            next = nxt;
        }
    }

    Entry<T> header, tail;
    int size;

    SinglyLinkedList1() {
        header = new Entry<>(null, null);
        tail = null;
        size = 0;
    }
    
    void add(T x) {
        if(tail == null) {
            header.next = new Entry<>(x, header.next);
            tail = header.next;
        } else {
            tail.next = new Entry<>(x, null);
            tail = tail.next;
        }
	size++;
    }
    int length()
    {
    	int length=0;
    	SinglyLinkedList1<T>.Entry<T> p=this.header;
    	while(p.next!=null)
    	{
    		length=length+1;
    		p=p.next;
    	}
    	return length;
    }
    void printList() {
        Entry<T> x = header.next;
        while(x != null) {
            System.out.print(x.element + " ");
            x = x.next;
        }
        System.out.println();
    }
 }