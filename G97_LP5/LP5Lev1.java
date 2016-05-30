import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author rbk
 *
 */
public class LP5Lev1 {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
		boolean VERBOSE = false;

		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}
		if (args.length > 1) {
			VERBOSE = true;
		}
		Graph g = Graph.readGraph(in, false); // read undirected graph from
		// stream "in"
		// Create your own class and call the function to find a maximum
		// matching.
		Boolean result = Matching.checkBipartite(g);
		int matched_edges = 0;
		if (!result)
			System.out.println("G is not bipartite");
		else {
			Timer t = new Timer();
			t.start();
			matched_edges = Matching.maximalMatching(g);
			t.end();
			System.out.println(t);
		}

		if (VERBOSE) {
			// Output the edges of M.
			System.out.println(matched_edges);
			Matching.printMatching(g);
		} else {
			System.out.println(matched_edges);
		}
	}
}
