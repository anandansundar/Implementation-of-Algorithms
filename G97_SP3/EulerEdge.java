
public class EulerEdge extends Edge {
	 public boolean seen;

	public EulerEdge(EulerVertex u, EulerVertex v, int w) {
		super(u, v, w);
		seen=false;
	}


}
