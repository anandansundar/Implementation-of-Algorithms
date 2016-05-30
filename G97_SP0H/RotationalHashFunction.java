/**
 * 
 * Rotational Hash Function  
 * 
 * @author Ramesh Suthan Palani,Rahul Aravind Mehalingam,Sanjana Ramakrishnan,Anandan Sundar
 *    
 */
public class RotationalHashFunction implements HashingFunction{

	
	/**
	 * Implementation of rotational hashing
	 * 
	 * Refer: http://www.eternallyconfuzzled.com/tuts/algorithms/jsw_tut_hashing.aspx
	 * @param o - object
	 * @param size - size of the bucket
	 * @return - hash value
	 */
	@Override
	public int getHash(Object o, int size) {
		int hash = o.hashCode();
		char[] ch = Integer.toUnsignedString(hash).toCharArray();
		int h = 0;
		int i;

		for (i = 0; i < ch.length; i++) {
			h = (h << 4) ^ (h >> 28) ^ ch[i];
		}
		if (h < 0) {
			h = h * -1;
		}

		return h % size;
	}

}
