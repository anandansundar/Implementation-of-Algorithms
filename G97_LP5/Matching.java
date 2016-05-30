import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

/**
 * @author Anandan Sundar, Ramesh Suthan Palani, Rahul Aravind Mehalingam,
 *         Sanjana Ramakrishnan
 *	
 *	Class to find maximum matching in a bipartite graph.
 */

public class Matching {

	public static final int outer = 1;
	public static final int inner = 2;

	/**
	 * Method to check whether the given graph is bipartite.
	 * 
	 * @param g 
	 * 			 : Instance of graph class.
	 * @return
	 * 			 : boolean whether indicating the graph is bipartite or not.
	 */
	public static boolean checkBipartite(Graph g) {

		// In a given graph, if there are odd length cycles, the graph is not bipartite.

		if (g.numNodes <= 2)
			return true;

		Queue<Vertex> q = new LinkedList<>();

		// Doing a Breadth First Search across all components in the graph.

		for (Vertex source : g) {

			if (!source.seen) {
				source.color = outer;
				source.seen = true;

				q.add(source);

				while (q.size() != 0) {

					Vertex u = q.poll();

					for (Edge e : u.Adj) {
						Vertex v = e.otherEnd(u);
						if (!v.seen) {
							v.seen = true;
							v.parent = u;
						}

						if (v.color != u.color) {
							if (v.color == 0) { //If the edge is not colored, color it with either inner or outer.
								v.color = (u.color == outer) ? inner : outer; 
								q.add(v);
							}
						} else
							return false; //Here two adjacent vertices have same color, so the graph is bipartite.
					}

				}
			}
		}
		return true;
	}

	/**
	 * Method to perform greedy matching in a given graph
	 * 
	 * @param g
	 * 			: Instance of graph class.
	 */

	public static void greedyMatching(Graph g) {

		// For an edge in a graph, if there is no mates in either ends, then make that as a matched edge.

		for (Edge e : g.edges) {
			if (e.From.mate == null && e.To.mate == null) {
				e.From.mate = e.To;
				e.To.mate = e.From;
				g.msize++;
			}
		}
	}

	/**
	 * Method to find the maximum matching of the given graph.
	 * 
	 * @param g
	 * 			: Instance of graph class.
	 * @return 
	 * 			: Number of matched edges.
	 */
	public static int maximalMatching(Graph g) {

		greedyMatching(g); //Matching the graph by greedy approach

		//Steps to find the augmented path, increase the size of the matching.
		outerloop: while (true) {
			Queue<Vertex> queue = new LinkedList<>();
			for (Vertex u : g) {
				u.seen = false;
				u.parent = null;
				if (u.mate == null && u.color == outer) {
					u.seen = true;
					queue.add(u);
				}
			}

			while (queue.size() != 0) {
				Vertex u = queue.poll();
				for (Edge e : u.Adj) {
					Vertex v = e.otherEnd(u);
					if (!v.seen) {
						v.parent = u;
						v.seen = true;
						if (v.mate == null) {
							// Augmented Path is found.
							// Since a path in a graph starts with outer vertex and ends with outer vertex.
							processAugPath(v); 
							g.msize++; //Increasing the size of matching by 1.
							continue outerloop;
						} else {
							Vertex x = v.mate;
							x.seen = true;
							x.parent = v;
							queue.add(x);
						}
					}
				}
			}
			//No augmented path has been found. Breaking the outer loop.
			break;

		}

		return g.msize;

	}

	/**
	 * 
	 * Method to process augmented path found in the given graph.
	 * 
	 * @param u 
	 * 			: Vertex u is a free inner node with an augmenting path to the root of the tree.
	 */
	private static void processAugPath(Vertex u) {
		
		//Processing Augmented Path
		Vertex p = u.parent;
		Vertex x = p.parent;
		Vertex nmx, y;
		u.mate = p;
		p.mate = u;
		p.color = inner; 
		u.color = outer;
		while (x != null) { // Go up to the grandparent of outer node x
			nmx = x.parent;
			y = nmx.parent;
			x.mate = nmx;
			nmx.mate = x;
			x.color = outer; //changing outer nodes as inner and vice versa as we go backard.
			nmx.color = inner;
			x = y;
		}
		return;
	}

	/**
	 * 
	 * Method to print matched edges in the given graph.
	 * @param g
	 * 			: Instance of graph class.
	 */
	public static void printMatching(Graph g){

		for (Vertex source : g){
			int weight = 0;
			if (source.color == inner){ //Getting the inner nodes and finding their matches to get the edge.
				ListIterator<Edge> it = source.Adj.listIterator();
				while (it.hasNext()){
					Edge e = it.next();
					if (e.otherEnd(source) == source.mate){ //The matched edge has been found. Getting its weight.
						weight = e.Weight;
						break;
					}
				}
				if (source.mate != null && source != null)
					System.out.println(source.mate + " " +  source + " " + weight);
			}
		}

	}
}
