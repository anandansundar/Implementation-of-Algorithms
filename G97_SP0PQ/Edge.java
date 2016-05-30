import java.util.Comparator;

public class Edge implements Comparator<Edge> {
	
	public Vertex From; //head vertex
	public Vertex To; //tail vertex
	public int Weight; //weight of the arc
	
	Edge() {}
	
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
	
	public int compare(Edge a, Edge b) {
		return ((a != null ? a.Weight : Integer.MAX_VALUE) - (b != null ? b.Weight : Integer.MAX_VALUE));
	}

}
