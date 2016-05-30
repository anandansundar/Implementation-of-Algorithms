/**
 * 
 */
package sp2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * @author Anandan
 *
 */

public class EulerianPath {
	
	//This function is to calculate the Degree of each vertex
	
	public int CalculateDegree(Vertex v){
		int degree = 0;
		System.out.println("The veritces which are adjacent to vertex " + v + " are " + v.Adj);
		degree = v.Adj.size();
		return degree;
	}
	
	//This function is to calculate the number of vertex count
	
	public int OddDegreeVertexCount(List<Vertex> vl) {
		int count = 0;
		int degreeofvertex;
		
		Iterator<Vertex> vi = vl.listIterator();
		
		while (vi.hasNext()){
			Vertex v = vi.next();
			degreeofvertex = CalculateDegree(v);
		
			if (degreeofvertex == 0) {
				System.out.println("The graph is unconnected with vertex " +v);
				count = -1;
				break;
			}
			
			if (degreeofvertex % 2 != 0){
				count++;
			}
		}
		
		if (count != -1)
		System.out.println("The number of vertices with odd degree is " + count);
		
		return count;	
		
	}
	
	//This function is to check the graph is Eulerian or not
	
	void testEulerian(Graph g) { 
		int cod;	
		List<Vertex> vertices = new ArrayList<>(g.verts);
		vertices.remove(0);
		System.out.println("The vertices of the graph are " +vertices);
		cod = OddDegreeVertexCount(vertices);
		Vertex FirstVertex = vertices.get(0);
		Vertex LastVertex = vertices.get(vertices.size()-1);
		if (cod == 0){
			System.out.println("The graph is an Eulerian Circuit");
		}
		else if (cod == 2){
			System.out.println("The graph has an Eulerian Path from " + FirstVertex + " to " + LastVertex);
		}
		else if (cod == -1){
			System.out.println("The graph is not connected");
		}
		else {
			System.out.println("The graph is not Eulierian, it has " + cod + " odd degree vertices");
		}
	}
	
	
	public static void main(String[] args) {
		
		EulerianPath ep = new EulerianPath();	
		boolean directed = false;
		Scanner in = new Scanner(System.in);
		Graph g = Graph.readGraph(in, directed);
		ep.testEulerian(g);
		
	}

}
