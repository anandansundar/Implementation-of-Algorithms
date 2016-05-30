import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author Rahul Aravind Mehalingam, Ramesh suthan palani, Anandan Sundar, Sanjana Ramakrishnan
 *
 */

public class Vertex implements Index, Comparator<Vertex> {
	
	public int name; // name of the vertex
	public boolean seen; //flag to check if the vertex has been visited or not
	public Vertex parent; //parent of the vertex
	public int distance; //distance to the vertex from source vertex
	public List<Edge> Adj, revAdj; // adjacency list; using linkedlist or arraylist
	public int indegree; //count of incoming edges
	public int index; // index information for indexed heap
	public int count; // count for bellman ford faster implementation
	public int top; // top for topological order
	public int numPaths; // count of shortest paths
	public boolean insideStack; // for cycle detection
	
	Vertex(int n) {
		name = n;
		seen = false;
		parent = null;
		Adj = new ArrayList<Edge>();
		revAdj = new ArrayList<Edge>(); // only for directed graphs
		numPaths = 0;
	}
	
	// set the index value
	public void putIndex(int idx) {
		index = idx;
	}
	
	//get the index value
	public int getIndex() {
		return index;
	}
	
	// compare function 
	public int compare(Vertex a, Vertex b) {
		return a.distance - b.distance;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + "";
	}
	

}
