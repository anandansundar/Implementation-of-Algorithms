import java.util.*;

public class SinglyLinkedList<T> {
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

    SinglyLinkedList() {
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

    void printList() {
        Entry<T> x = header.next;
        while(x != null) {
            System.out.print(x.element + " ");
            x = x.next;
        }
        System.out.println();
    }

     
    
    /**
     * Function: nonRecursiveReverseList
     * @param lst
     *  
     * Description: reverse the given linked list in a non-recursive way
     */
    public static<T> void nonRecursiveReverseList(SinglyLinkedList<T> lst){
    	SinglyLinkedList<T>.Entry<T> prevNode=null,currNode=null,nextNode=null;
    	
    	currNode=lst.header.next;
    	lst.tail=currNode;
    	
    	//prevNode-> points to the previous element in the linked list
    	//currNode-> points to the current element in the linked list being processed
    	//nextNode-> points to the next element in the linked list 
    	//Idea is when you process the current node in the linked list. you will store the nextNode in a buffer and point the current Node's 
    	//next pointer to previous Node.
    	while(currNode!=null){
    		nextNode=currNode.next;
    		currNode.next=prevNode;
    		prevNode=currNode;
    		currNode=nextNode;
    	}
    	lst.header.next=prevNode;
    	
    }
    
    /**
     * Function Name: recursiveReverseList
     * @param node
     * @param lst
     * @return Node
     * Description: reverses the given linked list in a recursive manner
     */
    public static<T> SinglyLinkedList<T>.Entry<T> recursiveReverseList(SinglyLinkedList<T>.Entry<T> node,SinglyLinkedList<T> lst){
    	
    	//list is empty return null
    	if(node==null){
    		return null;
    	}
    	
    	//when the next node is null => we reached the end of the list. 
    	//so update the header and tail to the point to this node(first node of the reversed linked list)
    	//return the first data node
    	if(node.next==null){    		
    		lst.header.next=node;
    		lst.tail=node;
    		return node;
    	}
    	
    	//call the function recursively using the next node in the list
    	//this function returns the next node in the list
    	//update the nextNode's next pointer to the current node and update the tail to the current node.
    	SinglyLinkedList<T>.Entry<T> nextNode = recursiveReverseList(node.next, lst);
    	nextNode.next=node;
    	node.next=null;
    	lst.tail=node;
    	return node;
    }
    
    
    /**Function Name: recursivePrintList
     * @param node
     * Description: print the linked list in the reverse order using a recursive way
     */
    public static<T> void recursivePrintList(SinglyLinkedList<T>.Entry<T> node){
    	//recurse until you reach the end of the list and then print the current element and return
    	if(node!=null){
    		recursivePrintList(node.next);
        	System.out.print(node.element+" ");
    	}
    }
    
    /**
     * Function Name: nonRecursivePrintList
     * @param node
     * Description: print the linked list in the reverse order using a non-recursive way
     */
    public static<T> void nonRecursivePrintList(SinglyLinkedList<T>.Entry<T> node){
    	Stack<T> stack=new Stack<T>();
    	//Push the node in the linked list one by one into stack until you reach the end of the list 
    	while(node!=null){
    		stack.push(node.element);
    		node=node.next;
    	}
    	
    	//Pop the top element from the stack and then print the element. repeat until stack is empty. 
    	while(!stack.isEmpty()){
    		System.out.print(stack.pop()+" ");
    	}
    	System.out.println();
    }

    

    public static void main(String[] args) {
        int n = 3;
        if(args.length > 0) {
            n = Integer.parseInt(args[0]);
        }

        SinglyLinkedList<Integer> lst = new SinglyLinkedList<>();
        for(int i=1; i<=4; i++) {
            lst.add(new Integer(i));
        }
        System.out.println("Original Input List:");
        lst.printList();
        
        //Reversing the list using recursion 
        System.out.println("Reversed List:(Recursive)");
        SinglyLinkedList.recursiveReverseList(lst.header.next, lst);
        lst.printList();
        
        //Reversing the reversed list again without recursion will restore the list order to its original state
        System.out.println("Reversed List:(Non-Recursive)");
        SinglyLinkedList.nonRecursiveReverseList(lst);
        lst.printList();
        
        System.out.println("Printing the list in reverse order(Recursive):");
        SinglyLinkedList.recursivePrintList(lst.header.next);
        System.out.println();
        
        System.out.println("Printing the list in reverse order(Non-Recursive):");
        SinglyLinkedList.nonRecursivePrintList(lst.header.next);
    }
}

/* Sample output:
Original Input List:
1 2 3 4 
Reversed List:(Recursive)
4 3 2 1 
Reversed List:(Non-Recursive)
1 2 3 4 
Printing the list in reverse order(Recursive):
4 3 2 1 
Printing the list in reverse order(Non-Recursive):
4 3 2 1 
*/