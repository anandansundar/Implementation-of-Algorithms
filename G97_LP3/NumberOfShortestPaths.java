import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @author Rahul Aravind Mehalingam, Ramesh suthan palani, Anandan Sundar, Sanjana Ramakrishnan
 *
 */

public class NumberOfShortestPaths {
	public Graph graph;
	public Vertex src;
	public final int INF = Integer.MAX_VALUE;
	public LinkedList<Vertex> topOrder;
	public boolean cycleFound = false;

	public NumberOfShortestPaths(Graph g, Vertex s) {
		graph = g;
		src = s;
		topOrder = new LinkedList<Vertex>();
	}

	public boolean customizedTopOrder() {

		// compute indegree for each vertex
		for (Vertex u : graph) {
			
			int activeEdge = 0;
			for (Edge e : u.revAdj) {
				if (e.isActive)
					activeEdge++;
			}
			u.indegree = activeEdge;
		}

		Queue<Vertex> queue = new LinkedList<Vertex>();

		// enqueue vertices which has zero indegree
		for (Vertex u : graph) {
			if (u.indegree == 0)
				queue.add(u);
		}


		while (!queue.isEmpty()) {
			Vertex u = queue.remove();
			topOrder.add(u);

			for (Edge e : u.Adj) {
				if(!e.isActive) continue;
				
				Vertex v = e.otherEnd(u);
				v.indegree--;

				if (v.indegree == 0) {
					queue.add(v);
				}
			}
		}

		//check if there is a directed cycle (Zero weighted / Negative)
		
		for(Vertex u : graph) {
			if(u.indegree != 0) return false; // there is a cycle
		}

		return true;
	}

	public boolean countTotalNumberOfShortestPaths() {

		for (Vertex u : graph) {
			if (u.distance == INF)
				continue;

			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);

				// equivalent condition
				if (v.distance == u.distance + e.Weight) {
					e.isActive = true;
				}
			}
		}

		boolean checkCycle = customizedTopOrder();
		//System.out.println("Cycle " + checkCycle);
		
		if (!checkCycle) {
			System.out.println( "Non-positive cycle in graph. DAC is not applicable");
			detectZeroCycle(src); // zero weighted cycle
			return false;
		}

		for (Vertex u : topOrder) {

			// skip the source vertex
			if (u == src) {
				u.numPaths = 1;
				continue;
			}

			int pathCount = 0;
			for (Edge e : u.revAdj) {
				if (!e.isActive)
					continue;

				Vertex v = e.otherEnd(u);
				pathCount += v.numPaths;
			}
			u.numPaths = pathCount;
		}

		return true;
	}

	public void printNoOfShortestPaths() {

		// calculating total number of shortest paths of all vertices from
		// source vertices

		long totCount = 0;
		
		for (Vertex u : topOrder) {
			totCount += u.numPaths;
		}
		
		System.out.println(totCount);
		if(graph.numNodes>100){
			return;
		}
		for (Vertex u : graph) {
			if (u.parent != null || u == src) {
				System.out.println(u.name + " " + u.distance + " " + u.numPaths);
			} else {
				System.out.println(u.name + " INF 0");
			}
		}
	}
	
	public void detectNegativeCycle(Vertex startVertex) {
		
		// Create an external stack
		Deque<Vertex> recStack = new ArrayDeque<Vertex>();
		
		// push the start vertex to the external stack
		recStack.push(startVertex);
		startVertex.insideStack = true;
		
		// define iterators for the adjacency list for each vertex
		Iterator<Edge>[] iterator = (Iterator<Edge>[]) new Iterator[graph.numNodes + 1];
		
		// skip the 0th iterator as the graph's src vertex starts at 1
		iterator[0] = null;
		
		// set iterators for each vertex's adjacency list
		int idx = 1;
		for(Vertex u : graph) {
			iterator[idx++] = u.Adj.iterator();
		}
		
		// cycle list to store the edges that form a negative cycle
		LinkedList<Edge> cycleList = new LinkedList<Edge>();
		
		
		// Loop until the external stack becomes empty
		while(!recStack.isEmpty()) {
			
			Vertex u = recStack.peek();
			
			// explore the adjacency list of each vertex
			if(iterator[u.name].hasNext())
			 {
				Edge e = iterator[u.name].next();	
				Vertex v = e.otherEnd(u);
				
				if(v.count < graph.numNodes) continue;
	
				if(v.insideStack) {
					cycleList.add(e);
					for(Edge edge : cycleList) {
						System.out.println(edge);
					}
					return;
				}
				
				cycleList.add(e);
				recStack.push(v); // equivalent to dfs(v)
				v.insideStack = true;
			 }
			else {	
				Vertex topV = recStack.pop();
				topV.insideStack = false;
				cycleList.removeLast();
			}
		}
		
	}
	
	public void detectZeroCycle(Vertex startVertex) {
		
		// Create an external stack
		Deque<Vertex> recStack = new ArrayDeque<Vertex>();
		
		// push the start vertex to the external stack
		recStack.push(startVertex);
		startVertex.insideStack = true;
		
		// define iterators for the adjacency list for each vertex
		Iterator<Edge>[] iterator = (Iterator<Edge>[]) new Iterator[graph.numNodes + 1];
		
		// skip the 0th iterator as the graph's src vertex starts at 1
		iterator[0] = null;
		
		// set iterators for each vertex's adjacency list
		int idx = 1;
		for(Vertex u : graph) {
			iterator[idx++] = u.Adj.iterator();
		}
		
		// cycle list to store the edges that form a negative cycle
		LinkedList<Edge> cycleList = new LinkedList<Edge>();
		
		
		// Loop until the external stack becomes empty
		while(!recStack.isEmpty()) {
			
			Vertex u = recStack.peek();
			
			// explore the adjacency list of each vertex
			if(iterator[u.name].hasNext())
			 {
				Edge e = iterator[u.name].next();
				
				if(!e.isActive) continue;
				
				Vertex v = e.otherEnd(u);
					
				if(v.insideStack) {
					cycleList.add(e);
					
					// print zero weighted cycle
					Vertex cycleStartVertex = cycleList.getLast().To;
					boolean print = false;
					for(Edge edge : cycleList) {
						if(!print && edge.From == cycleStartVertex) print = true;
						if(print) {
							System.out.println(edge);
						}
					}
					
					return;
				}
				
				cycleList.add(e);
				recStack.push(v); // equivalent to dfs(v)
				v.insideStack = true;
			 }
			else {	
				Vertex topV = recStack.pop();
				topV.insideStack = false;
				cycleList.removeLast();
			}
		}
		
	}
	
	public void printNegativeWeightedCycle() {
		
		Vertex startVertex = null;
		LinkedList<Edge> negativeCycle = new LinkedList<Edge>();
		
		//Locate the start vertex of the negative weight cycle
		for(Vertex u : graph) {
			if(u.count > graph.numNodes) {
				startVertex = u;
				break;
			}
		}
		
		Deque<Vertex> stack = new ArrayDeque<Vertex>();		
		LinkedList<Edge> cycleList = new LinkedList<Edge>();
		
		detectNegativeCycle(startVertex);
		
	}

}
