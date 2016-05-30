
public class Edge {
	public Vertex From; // head vertex
	public Vertex To; // tail vertex
	public int Weight; //weight of the arc
	
	Edge(Vertex from, Vertex to, int w) {
		From = from;
		To = to;
		Weight = w;
	}
	
	public Vertex otherEnd(Vertex from) {
		// if the vertex u is the head of the arc, then return the tail also return the head
		
		if(From == from) {
			return To;
		} else {
			return From;
		}
	}

}
