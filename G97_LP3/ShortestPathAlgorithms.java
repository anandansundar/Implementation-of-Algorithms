import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Implementation of shortest path algorithms
 * 
 * @author Rahul Aravind Mehalingam, Ramesh suthan palani, Anandan Sundar,
 *         Sanjana Ramakrishnan
 *
 */

public class ShortestPathAlgorithms {
	public static Queue<Vertex> topOrder = new LinkedList<Vertex>();
	public static String spAlgoName = "";
	public static final int INF = Integer.MAX_VALUE;

	/**
	 * Method to find the shortest path from the source s using BellmanFord
	 * Algorithm
	 * 
	 * @param g
	 *            - Graph
	 * @param s
	 *            - Vertex: Source
	 * @return - false if there is negative cycle in the given input graph
	 */

	public static boolean BellmanFord(Graph g, Vertex s) {

		for (Vertex u : g) {
			u.distance = INF;
			u.parent = null;
			u.count = 0;
			u.seen = false;
		}

		Queue<Vertex> queue = new LinkedList<Vertex>();

		s.distance = 0;
		s.seen = true;
		queue.add(s);

		while (!queue.isEmpty()) {
			Vertex u = queue.remove();
			u.seen = false;// no longer in queue
			u.count = u.count + 1;

			/*
			 * if(u.count >= g.numNodes) { System.out.println("Vertex name " +
			 * u.name + " " + " Vertex distance " + u.distance); }
			 */

			if (u.count > g.numNodes) {
				// System.out.println("Vertex name " + u.name + " " + " Vertex
				// distance " + u.distance);
				System.out.println("Negative cycle!!!");
				return false; // There is a negative cycle
			}

			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);
				if (v.distance > (u.distance + e.Weight)) {
					v.distance = u.distance + e.Weight;
					v.parent = u;
					if (!v.seen) {
						queue.add(v);
						v.seen = true;
					}
				}
			}
		}

		return true;
	}

	public static boolean Dijkstra(Graph g, Vertex s) {

		// Initialize
		for (Vertex u : g) {
			u.distance = INF;
			u.parent = null;
			u.count = 0;
			u.seen = false;
		}

		s.distance = 0;

		Vertex[] vertices_arr = new Vertex[g.numNodes + 1];
		vertices_arr[0] = null; // to avoid edge case

		int idx = 1;
		for (Vertex v : g.verts) {
			if (v != null) {
				vertices_arr[idx++] = v;
			}
		}

		for (int j = 1; j < vertices_arr.length; j++) {
			vertices_arr[j].index = j;
		}

		// Build a priority queue of vertices using vertex.distance as priority
		IndexedHeap<Vertex> pq = new IndexedHeap<Vertex>(vertices_arr, new Vertex(0));

		while (!pq.isEmpty()) {
			Vertex u = pq.remove();
			u.seen = true;

			// Relax edges out of u
			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);

				if (e.Weight < 0)
					return false; // Negative weight;

				if (v.distance > u.distance + e.Weight && !v.seen) {
					v.distance = u.distance + e.Weight;
					v.parent = u;
					pq.decreaseKey(v);
				}
			}
		}

		return true;
	}

	public static boolean getTopologicalOrder(Graph g) {

		// compute indegree for each vertex
		for (Vertex u : g) {
			u.indegree = u.revAdj.size();
		}

		Queue<Vertex> queue = new LinkedList<Vertex>();

		// enqueue vertices which has zero indegree
		for (Vertex u : g) {
			if (u.indegree == 0)
				queue.add(u);
		}

		int top = 0;

		while (!queue.isEmpty()) {
			Vertex u = queue.remove();
			topOrder.add(u);
			u.top = top++;

			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);
				v.indegree--;

				if (v.indegree == 0) {
					queue.add(v);
				}
			}
		}

		if (top != g.numNodes)
			return false; // there is a directed cycle in the directed graph;

		return true;

	}

	public static boolean DAGShortestPath(Graph g, Vertex s) {

		boolean checkCycle = getTopologicalOrder(g);

		if (!checkCycle)
			return false; // DAG shortest path cannot be applied on directed
							// acyclic graph;

		// Initialize
		for (Vertex u : g) {
			u.distance = INF;
			u.parent = null;
			u.count = 0;
			u.seen = false;
		}

		s.distance = 0;

		for (Vertex u : topOrder) {
			if (u.distance != Integer.MAX_VALUE) {
				for (Edge e : u.Adj) {
					// Relax edge
					Vertex v = e.otherEnd(u);
					if (v.distance > u.distance + e.Weight) {
						v.distance = u.distance + e.Weight;
						v.parent = u;
					}
				}
			}
		}

		return true;
	}

	public static boolean breadthFirstSearch(Graph g, Vertex s) {

		// Initialize
		for (Vertex u : g) {
			u.distance = INF;
			u.parent = null;
			u.count = 0;
			u.seen = false;
		}

		Queue<Vertex> queue = new LinkedList<Vertex>();

		s.distance = 0;
		s.seen = true;
		queue.add(s);

		while (!queue.isEmpty()) {
			Vertex u = queue.remove();

			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);

				if (!v.seen) {
					v.distance = u.distance + e.Weight;
					v.parent = u;
					v.seen = true;
					queue.add(v);
				}
			}
		}

		return true;
	}

	public static boolean findShortestPath(Graph g, Vertex src) {

		NumberOfShortestPaths numberOfShortestPaths = new NumberOfShortestPaths(g, src);

		boolean isEquivalent = true;
		boolean isNegative = false;
		int weight = Integer.MIN_VALUE;

		for (Vertex u : g) {
			for (Edge e : u.Adj) {
				if (weight == Integer.MIN_VALUE) {
					weight = e.Weight;

					if (weight < 0)
						isNegative = true;
				} else {
					if (e.Weight != weight) {
						isEquivalent = false;
					}
					if (isNegative == false && e.Weight < 0) {
						isNegative = true;
					}
				}
			}
		}

		if (isEquivalent && !isNegative) {
			spAlgoName = "BFS";
			breadthFirstSearch(g, src);
			return true;
		}

		boolean checkCycle = getTopologicalOrder(g);

		if (!checkCycle) {
			if (isNegative) {
				spAlgoName = "B-F";
				return BellmanFord(g, src);
			} else {
				spAlgoName = "Dij";
				Dijkstra(g, src);
			}
		} else {
			spAlgoName = "DAG";
			DAGShortestPath(g, src);
		}

		return true;
	}

	/**
	 * Method to print the output for the shortest path algorithms
	 * 
	 * @param g
	 *            - Graph
	 * @param s
	 *            - Source Vertex
	 */
	public static void printShortestPath(Graph g, Vertex s) {

		// calculating total sum of weights of vertices in shortest path
		long totWeight = 0;
		for (Vertex u : g) {
			if (u.distance != INF)
				totWeight += u.distance;
		}

		System.out.println(spAlgoName + " " + totWeight);

		if (g.numNodes > 100)
			return;

		for (Vertex u : g) {
			if (u == s) {
				System.out.println(u.name + " " + u.distance + " -");
			} else {
				if (u.parent != null) {
					System.out.println(u.name + " " + u.distance + " " + u.parent.name);
				} else {
					System.out.println(u.name + " INF -");
				}
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = null;

		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}

		Graph g = Graph.readGraph(in, true);

		LP3Level1.driver(g);
		LP3Level2.driver(g);

		/*
		 * for (File inputFile : new
		 * File("C://Users//RahulAravind//Downloads//lp3-no//lp3-data//lp3-no").
		 * listFiles()) { System.out.println(
		 * "-----------------------------------------------------------------------------------------"
		 * );
		 * 
		 * }
		 */
	}
}
