
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 
 * Generic Class to represent a graph
 * @author rameshsuthan
 *
 * @param <V> - Vertex Class 
 * @param <E> - Edge Class
 */
class Graph<V extends Vertex,E extends Edge> implements Iterable<V> {
	public V[] verts; // array of vertices
	public int numNodes; // number of verices in the graph
	public int numEdges; // number of Edges in the graph
	Class<V> vertexClass;
	Class<E> edgeClass;

	/**
	 * Constructor for Graph
	 * 
	 * @param size
	 *            : int - number of vertices
	 * @param edgeClass 
	 * @param vertexClass 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	Graph(int size,Class<V> vertexClass,Class<E> edgeClass) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//Class<? extends Vertex> vertexClass, Class<? extends Edge> edgeClass
		numNodes = size;
		this.vertexClass=vertexClass;
		this.edgeClass=edgeClass;
		//Create a array of Vertex Class Objects 
		verts = (V[])Array.newInstance(vertexClass, size+1);
		verts[0]= null;
		
		//Get the constructor method with one integer argument for instantiation of the vertex Object 
		Class paramTypes[]={int.class};
		Constructor<V> constructor = vertexClass.getConstructor(paramTypes);

		for (int i = 1; i <= size; i++){
			verts[i]= constructor.newInstance(i);
		}
		
	}

	/**
	 * Method to create the Edge Object 
	 * @param u
	 * @param v
	 * @param weight
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	E createEdge(V u,V v,int weight) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//Get the constructor of the Edge with given parameter Types for instantiation of the Edge objects
		Class paramTypes[]={vertexClass,vertexClass,int.class};
		Constructor<E> constructor = edgeClass.getConstructor(paramTypes);
		return constructor.newInstance(u,v,weight);
	}

	/**
	 * Method to add an edge to the graph
	 * 
	 * @param a
	 *            : int - one end of edge
	 * @param b
	 *            : int - other end of edge
	 * @param weight
	 *            : int - the weight of the edge
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	Edge addEdge(int a, int b, int weight) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		V u = verts[a];
		V v = verts[b];
		
		Edge e = createEdge(u,v,weight);
		u.Adj.add(e);
		v.Adj.add(e);
		
		numEdges++;
		return e;
	}

	/**
	 * Method to add an arc (directed edge) to the graph
	 * 
	 * @param a
	 *            : int - the head of the arc
	 * @param b
	 *            : int - the tail of the arc
	 * @param weight
	 *            : int - the weight of the arc
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	void addDirectedEdge(int a, int b, int weight) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		V head = verts[a];
		V tail = verts[b];
		Edge e = createEdge(head, tail, weight);
		head.Adj.add(e);
		tail.revAdj.add(e);
		numEdges++;
	}

	/**
	 * Method to create an instance of VertexIterator
	 */
	public Iterator<V> iterator() {
		return new VertexIterator();
	}

	/**
	 * A Custom Iterator Class for iterating through the vertices in a graph
	 * 
	 *
	 * @param <Vertex>
	 */
	private class VertexIterator implements Iterator<V> {
		private int index;

		/**
		 * Constructor for VertexIterator
		 * 
		 * @param v
		 *            : Array of vertices
		 * @param n
		 *            : int - Size of the graph
		 */
		private VertexIterator() {
			int index=1; // Index 0 is not used. Skip it.
		}

		/**
		 * Method to check if there is any vertex left in the iteration
		 * Overrides the default hasNext() method of Iterator Class
		 */
		public boolean hasNext() {
			return index<numNodes;
		}

		/**
		 * Method to return the next Vertex object in the iteration Overrides
		 * the default next() method of Iterator Class
		 */
		public V next() {
			return verts[++index];
		}

		/**
		 * Throws an error if a vertex is attempted to be removed
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * 
	 * Method to Read and create a graph.
	 * @param in
	 * @param directed
	 * @param vertexClass
	 * @param edgeClass
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static  <V extends Vertex,E extends Edge> Graph<V, E> readGraph(Scanner in, boolean directed,Class<V> vertexClass,Class<E> edgeClass) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	
		// read the graph related parameters
		int n = in.nextInt(); // number of vertices in the graph
		int m = in.nextInt(); // number of edges in the graph
		
		// create a graph instance
		Graph<V,E> g = new Graph<V,E>(n,vertexClass,edgeClass);
		for (int i = 0; i < m; i++) {
			int u = in.nextInt();
			int v = in.nextInt();
			int w = in.nextInt();
			if (directed) {
				g.addDirectedEdge(u, v, w);
			} else {
				g.addEdge(u, v, w);
			}
		}
		in.close();
		return g;				
	}
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, FileNotFoundException {
		
		Scanner in = null;

		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}

	
		Graph<EulerVertex,EulerEdge> graph=Graph.<EulerVertex,EulerEdge>readGraph(in, false,EulerVertex.class,EulerEdge.class);
		for(EulerVertex v:graph){
			System.out.println(v.name+"->"+v.Adj);
			
		}
		
	}
}
