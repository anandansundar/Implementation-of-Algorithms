import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class FindOddLengthCycle {
	
	void initialize(Graph g) {
		Iterator<Vertex> iterator = g.iterator();
		
		/***
		 * Initialize the graph by setting the values
		 * 
		 * seen to false
		 * parent to null
		 * distance to infinte.
		 */
		while(iterator.hasNext()) {
			Vertex vertex = iterator.next();
			vertex.seen = false;
			vertex.parent = null;
			vertex.distance = Integer.MAX_VALUE;
		}
	}
	
	List<Vertex> oddLengthCycle(Graph g) {
		
		List<Vertex> vertices = new ArrayList<Vertex>(); // Bipartite
		
		initialize(g);
		
		// Run BFS on every component
		
		Iterator<Vertex> iterator = g.iterator();
		Queue<Vertex> queue = new LinkedList<>();
		
		while(iterator.hasNext()) {
			Vertex vertex = iterator.next();
			
			if(!vertex.seen) {
				vertex.distance = 0;
				queue.add(vertex);
				vertex.seen = true;
				
				while(!queue.isEmpty()) {
					Vertex from = queue.remove();
					for (Edge e : from.Adj) {
						Vertex to = e.otherEnd(from);
						if(!to.seen) {
							to.seen = true;
							to.parent = from;
							to.distance = from.distance + 1;
							queue.add(to);
						} else {
							if(from.distance == to.distance) {
								// Non-Bipartite
								vertices.add(from);
								vertices.add(to);
								
								Vertex ua = from.parent;
								Vertex va = to.parent;
								
								while(ua != va) {
									vertices.add(ua);
									vertices.add(va);
									ua = ua.parent;
									va = va.parent;
								}
								
								vertices.add(ua);
								return vertices;
							}
						}
					}
				}
			}
		}
		
		
		return vertices; // empty list because the graph is bipartite.
		
	}
	
	public static void main(String args[]) {
		FindOddLengthCycle olc = new FindOddLengthCycle();
		
		Scanner in = new Scanner(System.in);
		boolean directed = false;
		
		Graph g = Graph.readGraph(in, directed);
		
		List<Vertex> vertices = olc.oddLengthCycle(g);
		
		System.out.println(vertices);
	}

}
