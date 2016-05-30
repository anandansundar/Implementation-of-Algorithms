Short Project 3
================

Main files for all programs:

A.Fibonacci.java
B.DualPivotQuickSort4.java
C.MergeSort.java, QuickSort.java
D.Select.java
F.Graph.java   

A. Fibonacci Numbers - Run Fibonacci.java - The time taken for computing fibonacci numbers using both O(n) and O(log n) algorithms are calculated.
	It is known that for small values of n - the O(n) algorithm beats the O(log n) algorithm. 
	Since p is relatively a small number, O(n) algorithm beats in running time.
	

B. Multipivot Quick Sort - Run DualPivotQuickSort4.java - The time taken to sort with the non-distinct elements in the array are calculated and displayed.

C. Merge sort vs Quick sort - Run MergeSort.java for Merge Sort. Run QuickSort2.java for Quick Sort. 
	The time taken to sort with the non-distinct elements in the array are calculated and displayed.
	
	Data taken for random number range (0 - 100000)
	
	Merge Sort										Quick Sort						  			Dual Pivot Quick Sort
	==========						                ==========                        			======================
	Array Size		Milliseconds				   Array Size		Milliseconds      			 Array Size		Milliseconds
				                                                                                
	1048576 		421				 			   1048576          373                  	     1048576 		 372       	
	4194304     	3215    		               4194304          1702                 	     4194304 		 1872
	16777216        9816	                       16777216         7483                  		 16777216		 9688
	67108864        42155   	                   67108864         37506                  		 67108864		 57534
	
D. Select - Run Select.java -K largest elements are computed by Partition algorithm and by using min heap datastructure and their performance are compared below. Results show that using Min heap(nlogk) is faster that the partition algorithm(klogn).

    Partition										Min heap						  			
	==========						                ==========                        		
	Array Size		Milliseconds				   Array Size		Milliseconds      		
				                                                                                
	1000000 		57 				 			   1000000          26                  	    	
	4194304     	93    		                   4194304          47                 	
	10000000        145   	                   	   10000000         76  
    16777216        215	                           16777216         129 	
	
F.(Generic Graph Class)
=========
Graph.java       
Edge.java        
Vertex.java      
EulerEdge.java   
EulerVertex.java 

javac *.java
java Graph.java

Sample Input:
============
6 10
1 2 1
1 3 1
1 4 1
1 6 1
2 3 1
3 6 1
3 4 1
4 5 1
4 6 1
5 6 1

Sample Output:
=============
1->[(1,2), (1,3), (1,4), (1,6)]
2->[(1,2), (2,3)]
3->[(1,3), (2,3), (3,6), (3,4)]
4->[(1,4), (3,4), (4,5), (4,6)]
5->[(4,5), (5,6)]
6->[(1,6), (3,6), (4,6), (5,6)]
