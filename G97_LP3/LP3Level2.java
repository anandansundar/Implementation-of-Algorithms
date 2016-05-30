
public class LP3Level2 {

	public static void driver(Graph g) {
		Vertex src = g.verts.get(1);
		NumberOfShortestPaths numberOfShortestPaths=new NumberOfShortestPaths(g, src);
		
		if (ShortestPathAlgorithms.findShortestPath(g, src)) {
			//shortest path Exist
			if(numberOfShortestPaths.countTotalNumberOfShortestPaths()){
			//shortest path is countable
				numberOfShortestPaths.printNoOfShortestPaths();
			}
			
		} else {
			System.out.println( "Non-positive cycle in graph. DAC is not applicable");
			numberOfShortestPaths.printNegativeWeightedCycle();
		}
	}

}
