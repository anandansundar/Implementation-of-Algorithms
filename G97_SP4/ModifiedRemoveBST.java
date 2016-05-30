
/** @author Anandan Sundar, Rahul Aravind Mehalingam, Ramesh Suthan Palani, Sanjana Ramakrishnan
 *  Java Program for modifying remove method from BST.
 **/

import java.util.*;

public class ModifiedRemoveBST<T extends Comparable<? super T>> {
	class Entry<T> {
		T element;
		Entry<T> left, right, parent;

		Entry(T x, Entry<T> l, Entry<T> r, Entry<T> p) {
			element = x;
			left = l;
			right = r;
			parent = p;
		}
	}

	Entry<T> root;
	int size;
	boolean remove_flag = true;

	ModifiedRemoveBST() {
		root = null;
		size = 0;
	}

	// Find x in subtree rooted at node t.  Returns node where search ends.

	Entry<T> find(Entry<T> t, T x) {
		Entry<T> pre = t;
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
		Entry<T> node = find(root, x);
		return node == null ? false : x.equals(node.element);
	}

	// Add x to tree.  If tree contains a node with same key, replace element by x.
	// Returns true if x is a new element added to tree.

	public boolean add(T x) {
		if(size == 0) {
			root = new Entry<>(x, null, null, null);
		} else {
			Entry<T> node = find(root, x);
			int cmp = x.compareTo(node.element);
			if(cmp == 0) {
				node.element = x;
				return false;
			}
			Entry<T> newNode = new Entry<>(x, null, null, node);
			if(cmp < 0) {
				node.left = newNode;
			} else {
				node.right = newNode;
			}
		}
		size++;
		return true;
	}

	// Remove x from tree.  Return x if found, otherwise return null

	public T remove(T x) {
		T rv = null;
		if(size > 0) {
			Entry<T> node = find(root, x);
			if(x.equals(node.element)) {
				rv = node.element;
				remove(node);
				size--;
			}
		}
		return rv;
	}

	// Called when node has at most one child.  Returns that child.

	Entry<T> oneChild(Entry<T> node) {
		return node.left == null? node.right : node.left;
	}

	// Remove a node from tree
	void remove(Entry<T> node) {
		if(node.left != null && node.right != null) {
			removeTwo(node, remove_flag);
			remove_flag = !remove_flag;
		} else {
			removeOne(node);
		}
	}

	// remove node that has at most one child

	void removeOne(Entry<T> node) {

		if(node == root) {
			root = oneChild(root);
			root.parent = null;
		} else {
			Entry<T> p = node.parent;
			Entry<T> nc = oneChild(node);
			if(p.left == node) {
				p.left = oneChild(node);
			} else {
				p.right = oneChild(node);
			}
			if (nc != null)
				nc.parent = p;
		}
	}

	// remove node that has two children

	void removeTwo(Entry<T> node, boolean flag) {

		if (flag){
			System.out.println("Replacing Max element in the left subtree");
			Entry<T> maxLeft = node.left;
			System.out.println("Left node : " + maxLeft.element);
			while(maxLeft.right != null){
				maxLeft = maxLeft.right;
			}
			System.out.println("Replacing " + maxLeft.element + " at the root");
			node.element = maxLeft.element;
			removeOne(maxLeft);
		}

		else {
			System.out.println("Replacing Min element in the right subtree");
			Entry<T> minRight = node.right;
			System.out.println("Right node : " + minRight.element);
			while(minRight.left != null) {
				minRight = minRight.left;
			}
			System.out.println("Replacing " + minRight.element + " at the root");
			node.element = minRight.element;
			removeOne(minRight);
		}
	}

	public static void main(String[] args) {
		ModifiedRemoveBST<Integer> t = new ModifiedRemoveBST<>();
		Scanner in = new Scanner(System.in);
		while(in.hasNext()) {
			int x = in.nextInt();
			if(x > 0) {
				System.out.print("Add " + x + " : ");
				t.add(x);
				t.printTree();
			} else if(x < 0) {
				System.out.print("Remove " + x + " : ");
				t.remove(-x);
				t.printTree();
			} else {
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
	int inOrder(Entry<T> node, Comparable[] arr, int index) {
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
	void printTree(Entry<T> node) {
		if(node != null) {
			printTree(node.left);
			System.out.print(" " + node.element);
			printTree(node.right);
		}
	}
}