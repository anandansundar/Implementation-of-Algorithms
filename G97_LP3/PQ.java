/**
 * 
 * @author Rahul Aravind Mehalingam, Ramesh suthan palani, Anandan Sundar, Sanjana Ramakrishnan
 *
 */

public interface PQ<T> {
	
	public void insert(T x);
	public T deleteMin();
	public T min();
	public void add(T x);
	public T remove();
	public T peek();

}
