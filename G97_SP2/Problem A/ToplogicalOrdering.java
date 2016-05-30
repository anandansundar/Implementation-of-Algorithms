import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 * 
 * Class to perform the topological ordering of a given graph
 * 
 * @author Ramesh Suthan Palani(RXP142630)
 *
 */
public class ToplogicalOrdering {

	/**
	 * Method to compute In Degree for each Vertex in the given graph
	 * 
	 * @param g
	 *            :Graph - input graph
	 */
	public static void computeInDegree(Graph g) {
		for (int i = 1; i <= g.numNodes; i++) {
			Vertex u = g.verts.get(i);
			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);
				v.inDegree++;
			}
		}
	}

	/**
	 * Method to order the nodes of a DAG topologically
	 * 
	 * @param g
	 *            :Graph - input graph
	 * @return :List<Vertex>: list of vertexes in topological order if the graph
	 *         is a DAG else return null
	 */
	public static List<Vertex> toplogicalOrder1(Graph g) {
		/*
		 * Algorithm 1. Remove vertices with no incoming edges, one at a time,
		 * along with their incident edges, and add them to a list.
		 */
		List<Vertex> orderedList = new LinkedList<Vertex>();

		Queue<Vertex> queue = new LinkedList<Vertex>();

		computeInDegree(g);

		for (int i = 1; i <= g.numNodes; i++) {
			Vertex u = g.verts.get(i);
			if (u.inDegree == 0) {
				queue.add(u);
			}
		}

		while (!queue.isEmpty()) {
			Vertex u = queue.remove();
			// adding to the list
			orderedList.add(u);

			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);
				v.inDegree--;
				if (v.inDegree == 0) {
					queue.add(v);
				}
			}
		}

		Iterator<Vertex> vertexIt = g.iterator();

		// at the end of the algorithm if there is a node with indegree not
		// equal to zero means then there is a cycle
		while (vertexIt.hasNext()) {
			Vertex u = vertexIt.next();
			if (u.inDegree != 0) {
				System.out.println("Graph has a cycle");
				return null;
			}
		}

		return orderedList;

	}

	/**
	 * 
	 * Method to order the perform DFS
	 * 
	 * @param u
	 *            :Vertex - on which dfs_visit needs to be done
	 * @param stack
	 *            :Stack<Vertex> - stack will contain the vertexes in
	 *            topological order
	 * @param isNodeInProgress
	 *            :boolean[] - to check whether the nodes is In Progress.
	 * @return :boolean returns true if there is no cycle in the dfs_visit
	 */
	public static boolean Dfs_Visit(Vertex u, Stack<Vertex> stack,
			boolean isNodeInProgress[]) {

		// set the node as seen and is present in the current recursion stack
		u.seen = true;
		isNodeInProgress[u.name] = true;
		// System.out.println("Visiting node+" + u.name);
		for (Edge e : u.Adj) {
			Vertex v = e.otherEnd(u);

			if (v.seen == false) {
				v.parent = u;
				if (!Dfs_Visit(v, stack, isNodeInProgress))
					return false;
			} else if (isNodeInProgress[v.name]) {
				// if the node is already visited and is still in progress
				// state,we have detected a cycle in the graph
				System.out.println("Graph has a cycle");
				return false;
			}
		}

		// if all the children nodes are DFS_VISITED then push the node in to
		// the output stack.
		stack.push(u);
		// set the isNodeInProgress to false. as the node processing is
		// completed
		isNodeInProgress[u.name] = false;
		return true;
	}

	/**
	 * Method to order the nodes of a DAG topologically
	 * 
	 * @param g
	 *            :Graph - input graph
	 * @return :Stack<Vertex> - stack of vertexes in topological order if the
	 *         graph is a DAG else return null
	 */
	public static Stack<Vertex> toplogicalOrder2(Graph g) {
		/*
		 * Algorithm 2. Run DFS on g and push nodes to a stack in the order in
		 * which they finish. Write code without using global variables.
		 */
		Stack<Vertex> stack = new Stack<>();
		Iterator<Vertex> vertexIt = g.iterator();

		// initialize the vertex
		while (vertexIt.hasNext()) {
			Vertex vertex = vertexIt.next();
			vertex.seen = false;
			vertex.parent = null;
		}

		vertexIt = g.iterator();
		// isNodeInProgress - used to store the nodes progress state to detect
		// cycle during dfs_visit
		boolean isNodeInProgress[] = new boolean[g.numNodes + 1];
		while (vertexIt.hasNext()) {
			Vertex u = vertexIt.next();
			if (!u.seen) {
				if (!Dfs_Visit(u, stack, isNodeInProgress)) {
					return null;
				}
			}
		}
		return stack;
	}

	/**
	 * Main Method *
	 * 
	 * @param args
	 *            :inputFile Location - String optional
	 * @throws FileNotFoundException
	 * 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = null;

		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}

		Graph g = Graph.readGraph(in, true);

		System.out.println("Using Toplogical Order 1:");
		List<Vertex> list = toplogicalOrder1(g);

		if (list != null) {
			for (Vertex v : list) {
				System.out.print(v.name + "->");
			}
		}
		System.out.println();

		System.out.println("Using Toplogical Order 2:");
		Stack<Vertex> stack = toplogicalOrder2(g);

		if (stack != null) {
			while (!stack.isEmpty()) {
				Vertex vertex = stack.pop();
				System.out.print(vertex.name + "->");

			}
		}
	}

}
