import java.util.ArrayList;
import java.util.HashSet;

/***
 * 
 * @author G97 - Rahul Aravind Mehalingam, Ramesh Suthan Palani, Anandan Sundar, Sanjana Ramakrishnan
 *
 */

public class Product {
	
	private long id;
	private double price;
	private ArrayList<Long> description;
	private int size;
	
	public Product(long id, double price, long[] description, int size) {
		super();
		this.id = id;
		this.price = price;
		this.description = new ArrayList<Long>();	
		for(int i = 0; i < size; i++) {
			this.description.add(description[i]);
		}
		
		this.size = size;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public ArrayList<Long> getDescription() {
		return description;
	}
	
	public void setDescription(ArrayList<Long> description) {
		this.description = description;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		String s = "";
		s += "Id: " + id;
		s += " price: " + price;
		s += " Description " + description;
		
		return s;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int xorVal = 0;
		
		for(Long descId : description) {
			xorVal ^= descId;
		}
		
		return xorVal;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof Product)) return false;
		
		Product product2 = (Product) obj;
		
		if (this.description.size() != product2.description.size()){
			return false;
		}
		
		HashSet<Long> descSet = new HashSet<>();
		
		for(Long descId : this.description) {
			descSet.add(descId);
		}
		
		
		for(Long descId : product2.description) {
			if(!descSet.contains(descId)) return false;
		}
		
		return true;
	}
	

}
