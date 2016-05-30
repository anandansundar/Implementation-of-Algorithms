import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Graph {
	public List<Vertex> verts; // array of vertices
	public int numNodes; // number of vertices in the graph
	
	Graph(int size) {
		numNodes = size;
		verts = new ArrayList<> (size + 1);
		verts.add(0, null);
		
		//create an array of Vertex objects
		for(int i = 1; i <= size; i++) 
			verts.add(i, new Vertex(i));
	}
	
	// Method to add an edge to the graph
	
		void addEdge(int a, int b, int weight) {
			Vertex from = verts.get(a);
			Vertex to = verts.get(b);
			Edge e = new Edge(from, to, weight);
			from.Adj.add(e);
			to.Adj.add(e);
		}
		
		// Method to add an arc (directed edge) to the graph
		
		void addDirectedEdge(int a, int b, int weight) {
			Vertex from = verts.get(a);
			Vertex to = verts.get(b);
			Edge e = new Edge(from, to, weight);
			from.Adj.add(e);
			to.revAdj.add(e);
			to.indegree++;
		}
		
		//Method to create an instance of VertexIterator
		
		public Iterator<Vertex> iterator() {
			return new VertexIterator();
		}
		
		 /**
		 * A Custom Iterator Class for iterating through the vertices in a graph
		 * 
		 *
		 * @param <Vertex>
		 */
		
		private class VertexIterator implements Iterator<Vertex> {
			private Iterator<Vertex> it;
			
			private VertexIterator() {
				it = verts.iterator();
				it.next(); // Index 0 is not used. skip it.
			}
			
			//Method to check if there is any vertex left
			
			public boolean hasNext() {
				return it.hasNext();
			}
			
			//Method to return the next vertex object in the iteration
			
			public Vertex next() {
				return it.next();
			}
			
			/**
			 * Throws an error if a vertex is attempted to be removed
			 */
			
			public void remove() {
			    throw new UnsupportedOperationException();
			}
		}
		
		public static Graph readGraph(Scanner in, boolean directed) {
			int n = in.nextInt(); // number of vertices
			int m = in.nextInt(); //number of edges
			
			//create a graph instance
			Graph g = new Graph(n);
			for(int i = 0; i < m; i++) {
				int u = in.nextInt();
				int v = in.nextInt();
				int w = in.nextInt();
				
				if(directed) {
					g.addDirectedEdge(u, v, w);
				} else {
					g.addEdge(u, v, w);
				}
			}
			
			in.close();
			return g;
		}

}
