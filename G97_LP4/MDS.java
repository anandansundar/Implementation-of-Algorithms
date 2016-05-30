import java.util.ArrayList;
import java.util.HashMap;
import java.util.NavigableMap;
import java.util.TreeMap;

/***
 * 
 * @author G97 - Rahul Aravind Mehalingam, Ramesh Suthan Palani, Anandan Sundar, Sanjana Ramakrishnan
 *
 */

public class MDS {
	
	public Index index = new Index();
	
	public static double roundOff(double val) {
		val = Math.floor((val + 0.000001) * 100) / 100;
		
		return val;
	}
	
	int insert(long id, double price, long[] description, int size) {
		// Description of item is in description[0..size-1].
		// Copy them into your data structure.
		Product product = new Product(id, price, description, size);
		int retVal = index.insert(product);
		//index.printIndex();
		
		return retVal;
	}

	double find(long id) {
		Product product = index.find(id);
		return (product != null) ? roundOff(product.getPrice()) : 0;
	}

	long delete(long id) {
		Product product=index.Delete(id);
		if(product == null){
			return 0;
		}
		
		long total = 0;
		for(long descId : product.getDescription()){
			total += descId;
		}		
		//index.printIndex();
		return total;
	}

	double findMinPrice(long des) {
		
		if(index.descIndex.size() == 0) {
			return -1;
		}
		
		TreeMap<Double, Integer> priceMap = index.descIndex.get(des);
		
		if(priceMap == null || priceMap.size() == 0) {
			return -1;
		}
		
		return roundOff(priceMap.firstKey());
	}

	double findMaxPrice(long des) {
		
		if(index.descIndex.size() == 0) {
			return -1;
		}
		
		TreeMap<Double, Integer> priceMap = index.descIndex.get(des);
		
		if(priceMap == null || priceMap.size() == 0) {
			return -1;
		}
		
		return roundOff(priceMap.lastKey());
	}

	int findPriceRange(long des, double lowPrice, double highPrice) {
		
		if(index.descIndex.size() == 0) {
			return 0;
		}
		
		TreeMap<Double, Integer> priceMap = index.descIndex.get(des);
		
		if(priceMap == null || priceMap.size() == 0) {
			return 0;
		}
		
		NavigableMap<Double, Integer> rangePriceMap = priceMap.subMap(lowPrice, true, highPrice, true);
		
		if(rangePriceMap == null || rangePriceMap.size() == 0) {
			return 0;
		}
		
		int totalItems = 0;
		for(Double price : rangePriceMap.keySet()) {
			totalItems += rangePriceMap.get(price);
		}
		
		return totalItems;
	}

	double priceHike(long minid, long maxid, double rate) {
		
		if(index.idIndex.size() == 0) {
			return 0;
		}
		
		NavigableMap<Long, Product> rangeIdMap = index.idIndex.subMap(minid, true, maxid, true);
		
		if(rangeIdMap == null || rangeIdMap.size() == 0) {
			return 0;
		}
		
		double netPrice = 0;
		for(Long id : rangeIdMap.keySet()) {
			Product product = rangeIdMap.get(id);
			
			double price = product.getPrice();
			double incPrice = (price * (1 + rate / 100));
			
			incPrice = roundOff(incPrice);
			
			netPrice += (incPrice - price);
			//update in all the indexes
			Product newProd = new Product(product.getId(), incPrice, null, 0);
			index.insert(newProd);
		}
		
		//System.out.println("Printing price index after price hike.....");
		//index.printIndex();
		
		return roundOff(netPrice);
	}

	int range(double lowPrice, double highPrice) {
		
		if(index.priceIndex.size() == 0) {
			return 0;
		}
		
		NavigableMap<Double, Integer> rangePriceMap = index.priceIndex.subMap(lowPrice, true, highPrice, true);
		
		if(rangePriceMap == null || rangePriceMap.size() == 0) {
			return 0;
		}
		
		int totalItems = 0;
		for(Double price : rangePriceMap.keySet()) {
			totalItems += rangePriceMap.get(price);
		}
		
		return totalItems;
	}

	int samesame() {
		
		TreeMap<Long, Product> idIndex = index.idIndex;
		HashMap<Product, Integer> sameSameDescIndex = new HashMap<Product, Integer>();
		int matches = 0;
		
		for(Product product : idIndex.values()) {
			ArrayList<Long> descList = product.getDescription();
			
			if(descList.size() < 8) continue;
			
			Integer count;
			if((count = sameSameDescIndex.get(product)) == null) {
				sameSameDescIndex.put(product, 1);
			} else {
				if(count == 1) {
					matches++;
				}
				matches++;
				sameSameDescIndex.put(product, count + 1);
			}
		}
		
		return matches;
	}
}