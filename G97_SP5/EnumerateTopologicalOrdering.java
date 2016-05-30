import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Rahul Aravind Mehalingam, Ramesh suthan palani, Anandan Sundar, Sanjana Ramakrishnan
 *
 */

public class EnumerateTopologicalOrdering {
	
	public static boolean isVertexProcessable(Vertex u) {
		for(Edge e : u.Adj) {
			Vertex v = e.otherEnd(u);
			if(v.seen) {
				return false;
			}
		}
		
		return true;
	}
	
	public static void permute(Graph g, ArrayList<Integer> tempList) {
		//System.out.println("#DEBUG " + tempList);
		if(tempList.size() == g.numNodes) {
			for(Integer elt : tempList) {
				System.out.print(elt + " ");
			}
			System.out.println();
			return;
		}
		
		for(Vertex v : g) {
			if(!v.seen) {
				if(isVertexProcessable(v)) {
					v.seen = true;
					tempList.add(v.name);
					//System.out.println("#DEBUG before " + tempList);
					permute(g, tempList);
					tempList.remove(tempList.size() - 1);
					//System.out.println("#DEBUG after " + tempList);
					v.seen = false;
				}
			}
		}
	}
	
	public static void enumerateTopologicalOrdering(Graph g) {
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		
		//initialize the seen value for each vertex
		for(Vertex v : g) {
			v.seen = false;
		}
		
		permute(g, tempList);
	}
	
	public static void main(String args[]) throws FileNotFoundException {
		
		Scanner in = null;

		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}

		Graph g = Graph.readGraph(in, true);
		
		enumerateTopologicalOrdering(g);
	}

}
