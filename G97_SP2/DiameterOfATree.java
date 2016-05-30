/**
 * Class to compute Diamter of a tree if the graph is a tree
 *
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;



public class DiameterOfATree {

	public static void main(String[] args)
	{
		try {
		Scanner in;
		DiameterOfATree d=new DiameterOfATree();
		if (args.length > 0) {
		    File inputFile = new File(args[0]);
		    
				in = new Scanner(inputFile);
		}
		 else {
		    in = new Scanner(System.in);
		}
		
		int n = in.nextInt(); // number of vertices in the graph
		int m = in.nextInt(); // number of edges in the graph

		// create a graph instance
		Graph g = new Graph(n);
		for (int i = 0; i < m; i++) {
		    int u = in.nextInt();
		    int v = in.nextInt();
		    int w = in.nextInt();
		    g.addEdge(u, v, w);
		}
		in.close();
		
		
		
		int returnVal=d.diameter(g);
		if(returnVal!=-1)
		{
		
		
		System.out.println("Diameter of the tree :"+returnVal);
		}
		else
		{
			System.out.println("The graph is not a tree");
		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method computes the diameter of the tree if the graph is a tree
	 * @param g :graph
	 * @return :int -diameter of the tree
	 */
	public int diameter(Graph g)
	{
		int sourceVertex=1;
		boolean isTree=isGraphATree(g,sourceVertex);
		if(isTree)
		{
		Vertex v=BFS(g, sourceVertex);
		
		Vertex u=BFS(g, v.name);
		
		
		return u.distance;
		}
		else
		{
			return -1;
		}
	}
	/**
	 * Checks whether the given graph is a tree
	 * @param g :graph
	 * @param sourceVertex : starting vertex
	 * @return : boolean - 
	 * 						true: it is a tree
	 */
	public boolean isGraphATree(Graph g, int sourceVertex)
	{
		boolean isTree=true;
		for(Vertex v:g)
		{
			v.seen=false;
			v.distance=0;
		}
		boolean isCyclePresent=DFS(g,g.verts.get(sourceVertex)); 
		if(!isCyclePresent) //if the graph is not connected then it is not a tree
		{
			for(Vertex u:g)
			{
				
				if(u.seen==false)
				{
					isTree=false;
					break;
				}
			}
		}
		else //if the graph contains a cycle, then again its not a tree
		{
			isTree=false;
		}
		
		
		return isTree;
	}
	
	/**
	 * Executes depth first search on the graph to detect cycle
	 * @param g :graph
	 * @param v :source vertex
	 * @return boolean : true indicates that the graph contains a cycle
	 */
	public boolean DFS(Graph g, Vertex v)
	{
		v.seen=true;
	
		for(Edge e:v.Adj)
		{
			Vertex u=e.otherEnd(v);
			if(u.seen==false)
			{
				u.parent=v;
				boolean isCyclePresent=	DFS(g,u);
				if(isCyclePresent)
					return true;
			}
			else if(v.parent!=u)//u.seen==true
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Executes BFS of the tree, computing distance of every vertex from source vertex
	 * @param g : graph
	 * @param sourceVertex
	 * @return Vertex which was last present in queue while computing BFS
	 */
	public Vertex BFS(Graph g, int sourceVertex)
	{
		for(Vertex v:g)
		{
			v.distance=0;
			v.seen=false;
		}
		
		Queue<Vertex> queue=new LinkedList<Vertex>();
		Vertex src=g.verts.get(sourceVertex);
		queue.add(src);
		src.seen=true;
		Vertex u=null;
		while(!queue.isEmpty())
		{
			u=queue.poll();
			for(Edge e:u.Adj)
			{
				Vertex v=e.otherEnd(u);
				if(!v.seen)
				{
					v.seen=true;
					v.distance=u.distance+1;
					queue.add(v);
				}
			}
			
		}
		return u;
	}
	
}

