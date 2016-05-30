import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class StronglyConnectedComponent {
	
	private void DFSUtil(Vertex vertex, Deque<Vertex> stack) {
		vertex.seen = true;
		
		//get Adjacency list
		List<Edge> adj = vertex.Adj;
		
		//Loop through the adjacent list of each vertex
		for(Edge e : adj) {
			Vertex toVertex = e.To;
			if(toVertex.seen) continue;
			DFSUtil(toVertex, stack);
		}
		stack.offerFirst(vertex);
	}
	
	private Graph transposeGraph(Graph g) {
		Graph gT = new Graph(g.numNodes);
		
		//retrieve the iterator
		Iterator<Vertex> iterator = g.iterator();
		
		//Loop through each vertex
		while(iterator.hasNext()) {
			Vertex vertex = iterator.next();
			List<Edge> edges = vertex.Adj;
			
			//reverse the directed edges
			for(Edge edge : edges) {
				gT.addDirectedEdge(edge.To.name, edge.From.name, edge.Weight);
			}
		}
		return gT;
	}
	
	int stronglyConnectedComponents(Graph g) {
	
		Deque<Vertex> stack = new ArrayDeque<Vertex>();
		
		Iterator<Vertex> iterator = g.iterator();
		
		//Do a DFS and store the vertex by their finish time
		while(iterator.hasNext()) {
			Vertex vertex = iterator.next();
			if(vertex.seen) continue;
			
			DFSUtil(vertex, stack);
		}
		
		//Transpose the graph
		Graph gT = transposeGraph(g);
		List<Vertex> vertices = gT.verts;
		
		int count = 0;
		
		//Pop each vertex out of the stack and do a dfs on the transposed graph
		while(!stack.isEmpty()) {
			Vertex vertex = vertices.get(stack.poll().name);
			if(vertex.seen) continue;
			
			DFSUtilForTransposeGraph(vertex);
			count++;
		}
		return count;
	}
	
	private void DFSUtilForTransposeGraph(Vertex vertex) {
		vertex.seen = true;
		List<Edge> edges = vertex.Adj;
		
		for(Edge edge : edges) {
			Vertex toVertex = edge.To;
			if(toVertex.seen) continue;
			DFSUtilForTransposeGraph(toVertex);
		}
	}
	
	public static void main(String args[]) {
		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		Scanner in = new Scanner(System.in);
		boolean directed = true;
		
		Graph g = Graph.readGraph(in, directed);
		int count = scc.stronglyConnectedComponents(g);
		
		System.out.println("Number of strongly connected components " + count);
	}

}
