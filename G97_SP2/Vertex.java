import java.util.ArrayList;
import java.util.List;

public class Vertex {
	
	public int name; // name of the vertex
	public boolean seen; // flag to check if the vertex has been visited.
	public Vertex parent; // parent of the vertex
	public int distance; // distance to the vertex from the source vertex
	public List<Edge> Adj, revAdj; // adjacency list; using LinkedList or ArrayList
	
	Vertex(int n) {
		name = n;
		seen = false;
		parent = null;
		Adj = new ArrayList<Edge>();
		revAdj = new ArrayList<Edge>(); /* only for directed graphs */
	}
	
	public String toString() {
		return Integer.toString(name);
	}

}
