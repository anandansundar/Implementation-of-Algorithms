Short Project 2
------------------

a)  Topological Ordering:

Note: Have added the following field in the Vertex.
public int inDegree;
Kindly use the Vertex file file provided under Problem A.

Run command: java ToplogicalOrdering <inputFile>
Argument: InputFile(optional): location of the file containing graph properties
if inputfile location is not provided, program will read the graph details from the command line.
    
Sample Input:
=============
7 8
1 3 1
1 5 1
3 7 1
5 2 1
7 6 1
7 4 1
2 6 1
2 4 1

Sample Output:
================
Reading graph with 7 vertices and 8 edges
Using Toplogical Order 1:
1->3->5->7->2->6->4->
Using Toplogical Order 2:
1->5->2->3->7->4->6->


b)Diameter Of a Tree

Run DiameterOfATree.java and provide the following details

1. Enter the number of nodes,number of edges. 
2.Provide details for each edge : its source vertex, destination vertex, weight.

Sample Output
=============
//Tree

6
5
1 6 0
1 2 0
2 3 0
2 5 0
3 4 0
Diameter of the tree :4

//Graph which is not a tree
6
6
1 6 0
1 2 0
2 3 0
2 5 0
3 4 0
6 3 0
The graph is not a tree

c) Strongly connected components of a directed graph

Run StronglyConnectedComponents.java and Provide the following details

Sample Input / Ouput:

11 
13 
1 2 0
2 3 0
3 1 0
2 4 0
4 5 0
5 6 0
6 4 0
7 6 0
7 8 0
8 9 0
9 10 0
10 7 0
10 11 0
Number of strongly connected components 4

d) Finding an odd length cycle in a non bipartite graph

Run FindOddLengthCycle.java and provide the following details

Sample Input / Output:

5
5
1 2 0
1 3 0
2 4 0
3 5 0
4 5 0
[4, 5, 2, 3, 1] // Odd Length cycle

Sample Input / Output:

6
6
1 2 0
2 3 0
3 4 0
4 5 0
5 6 0
6 1 0
[]

Sample Input / Output

6
7
1 2 0
2 3 0
1 3 0
3 4 0
4 5 0
5 6 0
4 6 0
[2, 3, 1]

e) Is the given graph Eulerian?

Run EulerianPath.java and provide the following details.

Sample Output
=============

Enter the number of vertices in the graph 
4

Enter the number of edges in the graph 
3

Enter the Source Vertex, Destination Vertex  and Weight of the edge in the graph 
1 2 1
2 3 1
3 4 1

The vertices of the graph are [1, 2, 3, 4]
The veritces which are adjacent to vertex 1 are [(1,2)]
The veritces which are adjacent to vertex 2 are [(1,2), (2,3)]
The veritces which are adjacent to vertex 3 are [(2,3), (3,4)]
The veritces which are adjacent to vertex 4 are [(3,4)]
The number of vertices with odd degree is 2
The graph has an Eulerian Path from 1 to 4