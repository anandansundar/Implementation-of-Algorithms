import java.util.*;


/**
 * @author Anandan Sundar, Rahul Aravind Mehalingam, Ramesh Suthan Palani, Sanjana Ramakrishnan
 *
 * Java Program for implementing Red Black tree  
 */

public class RBBST<T extends Comparable<? super T>> {

	private static final boolean RED   = false;
	private static final boolean BLACK = true;

	class RBEntry<T> {
		T element;
		RBEntry<T> left, right, parent;
		boolean color;

		RBEntry(T x, RBEntry<T> l, RBEntry<T> r, RBEntry<T> p) {
			element = x;
			left = l;
			right = r;
			parent = p;
			color = RED;
		}
	}


	RBEntry<T> root;
	int size;

	RBBST() {
		root = null;
		size = 0;
	}

	// Find x in subtree rooted at node t.  Returns node where search ends.
	RBEntry<T> find(RBEntry<T> t, T x) {
		RBEntry<T> pre = t;
		while(t != null) {
			pre = t;
			int cmp = x.compareTo(t.element);
			if(cmp == 0) {
				return t;
			} else if(cmp < 0) {
				t = t.left;
			} else {
				t = t.right;
			}
		}
		return pre;
	}

	// Is x contained in tree?
	
	public boolean contains(T x) {
		RBEntry<T> node = find(root, x);
		return node == null ? false : x.equals(node.element);
	}

	// Add x to tree.  If tree contains a node with same key, replace element by x.
	// Returns true if x is a new element added to tree.
	
	public boolean add(T x) {
		if(size == 0) {
			root = new RBEntry<>(x, null, null, null);
			root.color = BLACK;
		} 

		else {

			RBEntry<T> node = find(root, x);
			int cmp = x.compareTo(node.element);

			if(cmp == 0) {
				node.element = x;
				return false;
			}

			RBEntry<T> newNode = new RBEntry<>(x, null, null, node);
			if(cmp < 0) {

				node.left = newNode;

			} 

			else {

				node.right = newNode;

			}

			AddressViolation(newNode);
		}

		size++;
		return true;
	}

	// Function to address the violation which may cause in inserting nodes in the Red-Black Tree

	public void AddressViolation(RBEntry<T> newNode) {

		while ((newNode != root) && (newNode.color == RED) && (newNode.parent.color == RED)){

			RBEntry<T> grand_parent = newNode.parent.parent;
			RBEntry<T> par = newNode.parent;

			if (newNode.parent == grand_parent.left){

				RBEntry<T> uncle = grand_parent.right;

				if(uncle != null && uncle.color == RED){
					grand_parent.color = RED;
					newNode.parent.color = BLACK;
					uncle.color = BLACK;
					newNode = grand_parent;
				}

				else {
					if (newNode == newNode.parent.right){
						LeftRotation(par);
						newNode = par;
						par = newNode.parent;
					}

					RightRotation(grand_parent);
					par.color = BLACK;
					grand_parent.color = RED;
					newNode = par;
				}

			}

			else {

				RBEntry<T> uncle = grand_parent.left;

				if ((uncle != null) && (uncle.color == RED)){
					grand_parent.color = RED;
					par.color = BLACK;
					uncle.color = BLACK;
					newNode = grand_parent;
				}

				else {

					if (newNode == par.left){
						RightRotation(par);
						newNode = newNode.parent;
						par = newNode.parent;
					}

					LeftRotation(grand_parent);
					par.color = BLACK;
					grand_parent.color = RED;
					newNode = par;
				}
			}
			root.color = BLACK;
		}

	}

	// Function to do a right-rotate	
	
	public void RightRotation(RBBST<T>.RBEntry<T> node) {

		RBEntry<T> left_node = node.left;
		node.left = left_node.right;

		if (node.left != null){
			node.left.parent = node;
		}

		left_node.parent = node.parent;

		if (node.parent == null){
			root = left_node;
		}

		else if (node == node.parent.left){
			node.parent.left = left_node;
		}

		else {
			node.parent.right = left_node;
		}

		left_node.right = left_node;
		node.parent = left_node;

	}
	
	//Function to do left-rotate
	
	public void LeftRotation(RBBST<T>.RBEntry<T> node) {

		RBEntry<T> right_node = node.right;
		node.right = right_node.left;

		if (node.right != null){
			node.right.parent = node;
		}

		right_node.parent = node.parent;

		if (node.parent == null){
			root = right_node;
		}

		else if (node == node.parent.left){
			node.parent.left = right_node;
		}

		else {
			node.parent.right = right_node;
		}

		right_node.left = node;
		node.parent = right_node;

	}

	// Called when node has at most one child.  Returns that child.
	RBEntry<T> oneChild(RBEntry<T> node) {
		return node.left == null? node.right : node.left;
	}

	public static void main(String[] args) {
		RBBST<Integer> t = new RBBST<>();
		Scanner in = new Scanner(System.in);
		while(in.hasNext()) {
			int x = in.nextInt();
			if(x > 0) {
				System.out.print("Add " + x + " : ");
				t.add(x);
				t.printTree();
			} 
			else {
				Comparable[] arr = t.toArray();
				System.out.print("Final: ");
				for(int i=0; i<t.size; i++) {
					System.out.print(arr[i] + " ");
				}
				System.out.println();
				return;
			}		
		}

	}

	// Create an array with the elements using in-order traversal of tree
	public Comparable[] toArray() {
		Comparable[] arr = new Comparable[size];
		inOrder(root, arr, 0);
		return arr;
	}

	// Recursive in-order traversal of tree rooted at "node".
	// "index" is next element of array to be written.
	// Returns index of next entry of arr to be written.
	int inOrder(RBEntry<T> node, Comparable[] arr, int index) {
		if(node != null) {
			index = inOrder(node.left, arr, index);
			arr[index++] = node.element;
			index = inOrder(node.right, arr, index);
		}
		return index;
	}

	public void printTree() {
		System.out.print("[" + size + "]");
		printTree(root);
		System.out.println();
	}

	// Inorder traversal of tree
	void printTree(RBEntry<T> node) {
		if(node != null) {
			printTree(node.left);
			System.out.print(" " + node.element);
			printTree(node.right);
		}
	}
}