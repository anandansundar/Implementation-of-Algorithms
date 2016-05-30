import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/***
 * 
 * @author G97 - Rahul Aravind Mehalingam, Ramesh Suthan Palani, Anandan Sundar, Sanjana Ramakrishnan
 *
 */

public class Index {
	
	public TreeMap<Long, Product> idIndex;
	public HashMap<Long, TreeMap<Double, Integer>> descIndex;
	public TreeMap<Double, Integer> priceIndex;
	
	public Index() {
		super();
		this.idIndex = new TreeMap<>();
		this.descIndex = new HashMap<>();
		this.priceIndex = new TreeMap<>();
	}
	
	private void insertIntoPriceIndex(Product product) {
		
		Integer count;
		if((count = priceIndex.get(product.getPrice())) == null) {
			priceIndex.put(product.getPrice(), 1);
		} else {
			priceIndex.put(product.getPrice(), count + 1);
		}
	}
	
	private void updatePriceIndex(Product oldProd, Product newProd) {
		
		deletePrice(oldProd);
		insertIntoPriceIndex(newProd);
	}
	
	private void updateDescIndex(Product oldProd, Product newProd) {
		
		// update the description index accordingly by iterating over the old desc list
		deleteDescIndex(oldProd);
		
		// update the description index accordingly by iterating over the new desc list
		insertIntoDescIndex(newProd);
	}
	
	public void updatePriceInDescIndex(Product product, double oldPrice, double newPrice) {
		
		// delete the existing price entries in description index and add the updated price in the index
		for(Long descId : product.getDescription()) {
			TreeMap<Double, Integer> priceMap = descIndex.get(descId);
			
			Integer count;
			if((count = priceMap.get(oldPrice)) == 1) {
				priceMap.remove(oldPrice);
			} else {
				priceMap.put(oldPrice, count - 1);
			}
			
			if((count = priceMap.get(newPrice)) == null) {
				priceMap.put(newPrice, 1);
			} else {
				priceMap.put(newPrice, count + 1);
			}
		}
		
	}
	
	private void insertIntoDescIndex(Product product) {
		
		ArrayList<Long> descList = product.getDescription();
		
		for(Long descId : descList) {
			
			TreeMap<Double, Integer> priceMap;
			if((priceMap = descIndex.get(descId)) == null) {
				priceMap = new TreeMap<>();
				priceMap.put(product.getPrice(), 1);
				descIndex.put(descId, priceMap);
			}
			else {
				Integer count;
				if((count = priceMap.get(product.getPrice())) == null){
					priceMap.put(product.getPrice(), 1);
				} else {
					priceMap.put(product.getPrice(), count + 1);
				}
			}
		}
	}
	
	public int insert(Product product) {
		
		long productId = product.getId();
		//Insert the product into idIndex
		Product oldProd;
		if((oldProd = idIndex.get(productId)) == null) {
			idIndex.put(productId, product);
			
			insertIntoDescIndex(product);
			insertIntoPriceIndex(product);
			
			return 1;
		} else {
			
			ArrayList<Long> desc = product.getDescription();
			
			if(desc.size() > 0) {
				updateDescIndex(oldProd, product);
				oldProd.setDescription(product.getDescription());
			} else {
				updatePriceInDescIndex(oldProd, oldProd.getPrice(), product.getPrice());
			}
			
			updatePriceIndex(oldProd, product);
			oldProd.setPrice(product.getPrice());
			
			return 0;
		}
	}

	public Product find(long id) {
		
		return idIndex.get(id);
	}
	
	private void deleteDescIndex(Product product) {
		
		ArrayList<Long> descList = product.getDescription();
		double price = product.getPrice();
		
		// update the description index accordingly by iterating over the old desc list
		for(Long descId : descList) {
			
			TreeMap<Double, Integer> priceMap = descIndex.get(descId);
			
			Integer count;
			if((count = priceMap.get(price)) == 1) {
				
				if(priceMap.size() == 1) {
					descIndex.remove(descId);
					continue;
				}
				
				priceMap.remove(price);
			} else {
				priceMap.put(price, count - 1);
			}
		}
	}
	
	private void deletePrice(Product product) {
		
		Double price = product.getPrice();
		
		Integer count;
		if((count = priceIndex.get(price)) == 1) {
			priceIndex.remove(price);
		} else {
			priceIndex.put(price, count - 1);
		}
	}
	
	public Product Delete(long id) {
				
		// delete the entry from main Index
		Product delProduct = idIndex.remove(id);
		
		if(delProduct == null)
			return null;
		
		deleteDescIndex(delProduct);
		deletePrice(delProduct);
		
		return delProduct;
	}
	
	/*public void printIndex() {
		
		System.out.println("****************************************************");
		System.out.println("ID Index " + idIndex);
		System.out.println("****************************************************");
		
		System.out.println("****************************************************");
		for(long descId : descIndex.keySet()) {
			System.out.println("DescId " + descId);
			System.out.println(descIndex.get(descId));
		}
		System.out.println("****************************************************");
		
		System.out.println("****************************************************");
		System.out.println("Price Index " + priceIndex);
	}*/

}
