
public class LP3Level1 {
	
	
	
	public static void driver(Graph g){
		Vertex src = g.verts.get(1);
		if(ShortestPathAlgorithms.findShortestPath(g, src)){
			ShortestPathAlgorithms.printShortestPath(g, src);
		}else{
			System.out.println("Unable to solve problem. Graph has a negative cycle");
		}
	}

}
