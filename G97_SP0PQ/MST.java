import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MST {
	
	//Finding MST using Prim's algorithm using Binary heap
	static int PrimMST(Graph g) {
		int wmst = 0;
		Vertex src = g.verts.get(1);
		src.seen = true;
		
		BinaryHeap<Edge> pq = new BinaryHeap<Edge>(g.numNodes * 2, new Edge());
		for (Edge e : src.Adj)
			pq.add(e);
		
		//Loop through each and every edge
		while(!pq.isEmpty()) {
			Edge e = pq.remove();
			if(e.From.seen && e.To.seen) continue;
			
			Vertex v;
			if(!e.From.seen) v = e.From;
			else v = e.To;
			
			v.seen = true;
			wmst += e.Weight;
			
			for(Edge edge : v.Adj) {
				Vertex u = edge.otherEnd(v);
				if(!u.seen) pq.add(edge);
			}
		}
		return wmst;
	}
	
	//Finding MST using Binary Indexed Heap
	static int PrimMSTUsingIndexedHeap(Graph g) {
		int wmst = 0;
		
		// create a vertices array.
		// set the distance of each vertex to infinity.
		
		Vertex[] verts_arr = new Vertex[g.numNodes + 1];
		verts_arr[0] = null; // to avoid edge case condition
		
		int idx = 1;
		for(Vertex v : g.verts) {
			if(v != null) {
				v.distance = Integer.MAX_VALUE;
				verts_arr[idx++] = v;
			}
		}
		
		//set the distance of the source vertex to zero.
		verts_arr[1].distance = 0;
		
		//set the initial indexes for the vertices
		for(int j = 1; j < verts_arr.length; j++) {
			verts_arr[j].index = j;
		}
		
		IndexedHeap<Vertex> IndexPQ = new IndexedHeap<Vertex>(verts_arr, new Vertex(0));
		
		//Loop through each vertex untill for all vertices, the minimum distance is set
		// and the heap is empty
		
		while(!IndexPQ.isEmpty()) {
			Vertex from = IndexPQ.remove();
			
			from.seen = true;
			wmst += from.distance;
			for(Edge e : from.Adj) {
				Vertex to = e.otherEnd(from);
				if(!to.seen && to.distance > e.Weight) {
					to.distance = e.Weight;
					to.parent = from;
					IndexPQ.decreaseKey(to);
				}
			}
			
		}
		
		return wmst;
	}
	
	public static void main(String args[]) throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
        Graph g = Graph.readGraph(in, false);
        System.out.println(PrimMST(g));
		
		/*
		File dir=new File("Data");
		for(File file:dir.listFiles()){
			
			System.out.println("Input File:"+file.getName());
			in = new Scanner(file);
			Graph g = Graph.readGraph(in, false);			
			System.out.println("MST using Binary Heap");
			long startTime = System.currentTimeMillis();
			//int wmst = PrimMSTUsingIndexedHeap(g);
			int wmst = PrimMST(g);
			long endTime = System.currentTimeMillis();
			System.out.println("Weight of the Minimum spanning tree " + wmst);
			long elapsedtime = endTime - startTime;
			System.out.println("Time taken " + elapsedtime + " ms");
			
			in = new Scanner(file);
			g = Graph.readGraph(in, false);
			System.out.println("MST using Indexed Heap");
			long startTime1 = System.currentTimeMillis();
			int wmst1 = PrimMSTUsingIndexedHeap(g);
			//int wmst1 = PrimMST(g);
			long endTime1 = System.currentTimeMillis();
			System.out.println("Weight of the Minimum spanning tree " + wmst1);
			long elapsedtime1 = endTime1 - startTime1;
			System.out.println("Time taken " + elapsedtime1 + " ms");
			System.out.println();
		}
		*/
	}
	

}
