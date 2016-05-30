package numlib;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class BigNumber {
	ArrayList<Long> digits; 
	boolean isPositive;
	final int base = 10;
	public static long count=0;
	
	public BigNumber() {
		digits = new ArrayList<Long>();
		count++;
	}
	
	public BigNumber(String s) {
		count++;
		digits = new ArrayList<Long>();
		
		s = s.trim();
		
		if(s.isEmpty())
			return;
		
		char ch;
		int index;
		
		if(s.charAt(0) == '+') {
			isPositive = true;
			index = 1;
		}
		else if(s.charAt(0) == '-'){
			isPositive = false;
			index = 1;
		}
		else {
			isPositive = true;
			index = 0;
		}
		
		for(int i = s.length() - 1; i >= index; i--) {
			ch = s.charAt(i);
			digits.add((long) (ch-'0'));
		}
		
		for(int i = digits.size() - 1; i > 0; i--) {
			if(digits.get(i) == 0L) digits.remove(i);
			else break;
		}
		
		if(digits.size() == 1 && digits.get(0) == 0L) isPositive = true;
		
		//converting from base10 to given base
		BaseConverter a = new BaseConverter(s);
		BaseConverter b = new BaseConverter(base + "");

		BaseConverter result = BaseConverter.convertFrombase10(a, b);
		
		this.digits=result.digits;
		//System.out.println("Testing"+digits+"=="+s);
	}
	
	public BigNumber(Long num) {
		count++;
		digits = new ArrayList<Long>();
		
		if(num == 0) {
			isPositive = true;
			digits.add(0L);
			return;
		}
		
		if(num>0) {
			isPositive = true;
		}
		else {
			isPositive = false;
			num = num * -1;
		}
		Long remainder;
		
		while(num > 0) {
			remainder = num % base;
			num /= base;
			digits.add(remainder);
		}
	}
	
	@Override
	public String toString() {
		String num = "";
	
		//converting from the given base to base 10
		BaseConverter a = new BaseConverter();
		a.digits = this.digits;
		
		BaseConverter b = new BaseConverter(base + "");

		BaseConverter result = BaseConverter.convertTobase10(a, b);
		
		if(isPositive == false) {
			num += "-";
		}
		
		for(int i = result.digits.size() - 1; i >= 0; i--) {
			num += result.digits.get(i)+ "";
		}
		
		return num;
		
	}
	
	
	public void printList()
	{
		System.out.print("Base "+ base +": ");
		
		if(!isPositive) {
			System.out.print("-");
		}
		for(long i : digits) {
			System.out.print(i + " ");
		}
	}
	
	public BigNumber splitBigNumber(int fromIndex, int toIndex) {
		BigNumber res = new BigNumber();
		
		//System.out.println("#DEBUG split : " + this.digits);
		
		for(int i = fromIndex; i < toIndex; i++) {
			res.digits.add(this.digits.get(i));
		}
		res.isPositive = this.isPositive;
		
		return res;
	}
	
	public static BigNumber addUtil(BigNumber num1, BigNumber num2) {
		BigNumber res = new BigNumber();
		ArrayList<Long> x = num1.digits;
		ArrayList<Long> y = num2.digits;
		
		int b = num1.base;
		
		// If both x and y are null, then return nothing
		if(x == null && y == null) return res;
		
		// If x is null and y is not null, then return y which is the result
		if(x == null || x.isEmpty()) res.digits.addAll(y);
		
		// If y is null and x is not null, then x is the result
		if(y == null || y.isEmpty()) res.digits.addAll(x);
		
		//Fetching iterators
		Iterator<Long> x_iter = x.iterator();
		Iterator<Long> y_iter = y.iterator();
		
		Long carry = 0L;
		
		// Adding the numbers till either x or y gets empty 
		while(x_iter.hasNext() && y_iter.hasNext()) {
			Long val = x_iter.next() + y_iter.next() + carry;
			carry = val / b;
			val = val % b;
			res.digits.add(val);
		}
		
		//If there are remaining elements in x, they are added to the result and carry is kept track of
		while(x_iter.hasNext()) {
			Long val = x_iter.next() + carry;
			carry = val / b;
			val = val % b;
			res.digits.add(val);
		}
		
		// If there are remaining elements in y, they are added to the result and carry is kept track of
		while(y_iter.hasNext()) {
			Long val = y_iter.next() + carry;
			carry = val / b;
			val = val % b;
			res.digits.add(val);
		}
		
		//If carry exists, its added to the result.
		if(carry > 0) res.digits.add(carry);
		
		if(num1.isPositive == false || num2.isPositive == false) {
			res.isPositive = false;
		}
		
		return res;
	}
	
	public static BigNumber subtractUtil(BigNumber num1, BigNumber num2) {
		BigNumber res = new BigNumber();
		ArrayList<Long> x = num1.digits;
		ArrayList<Long> y = num2.digits;
		
		int b = num1.base;
		
		// If both x and y are null, return nothing
		if(x == null && y == null) return res;
		
		// if x is not null and y is empty, then return x which is the result
		if(y == null || y.isEmpty()) res.digits.addAll(x);
		
		//Fetching the iterators
		Iterator<Long> x_iter = x.iterator();
		Iterator<Long> y_iter = y.iterator();
		
		Long carry = 0L;
		Long temp_x = 0L;
		Long temp_y = 0L;
		
		//Track the first occurence of non-zero
		boolean chkNonZero = false;
		
		//track the current position of the list on every iteration
		int idx = 0;
		
		//track the most significant non-zero index
		int msbIdx = 0;
		
		//Loop through the elements of the array.
		while(x_iter.hasNext()) {
			temp_x = x_iter.next();
			
			//If the y list size is less than x list, then zeroes are padded.
			temp_y = y_iter.hasNext() ? y_iter.next() : 0;
			
			if(!chkNonZero) {
				if(temp_y != 0) {
					chkNonZero = true;
					temp_y = b - temp_y;
				}
			}
			else temp_y = (b - 1) - temp_y;
			
			Long val = temp_x + temp_y + carry;
			carry = val / b;
			val = val % b;
			res.digits.add(val);
			if(val != 0) {
				msbIdx = idx;
			}
			idx++;
		}
		if(msbIdx + 1 < res.digits.size()) res.digits.subList(msbIdx + 1, res.digits.size()).clear();
		
		return res;
	}
	
	public static BigNumber subtract(BigNumber a, BigNumber b) {
		BigNumber res = null;
		
		if(a == null || b == null) return res;
		
		/**
		 * If a is +ve and b is +ve, then res = a - b
		 */
		
		
		if(a.isPositive && b.isPositive){
			if(isLesserThan(a, b)) {
				res = subtractUtil(b, a);
				res.isPositive = false;
			}
			else {
				res = subtractUtil(a, b);
				res.isPositive = true;
			}
			return res;
		}
		
		/**
		 * 
		 * If a is -ve and b is -ve, then res = -a - (-b) => -(a-b)
		 */
		
		else if(!a.isPositive && !b.isPositive) {
			if(isLesserThan(a, b)) {
				res = subtractUtil(b,  a);
				res.isPositive = true;
			}
			else {
				res = subtractUtil(a, b);
				res.isPositive = false;
			}
			
			return res;
		}
		
		/***
		 * 
		 * If a is -ve and b is  + ve, then res = -a - b => -(a + b)
		 * 
		 * similarly, If a is +ve and b is -ve, then res = a - (-b) => a + b
		 */
		
		else if(!a.isPositive && b.isPositive) {
			res = addUtil(a, b);
			res.isPositive = false;
			
			return res;
		}
		
		else {
			res = addUtil(a, b);
			res.isPositive = true;
			
			return res;
		}
		
	}
	
	public static BigNumber add(BigNumber a, BigNumber b) {
		
		BigNumber res = null;
		
		if(a == null || b == null) return res;
		
		/**
		 * If a is +ve and b is +ve, then a+b
		 */
		
		if(a.isPositive && b.isPositive) {
			res = addUtil(a, b);
			res.isPositive = true;
			
			return res;
		}
		
		/**
		 * If a is -ve and b is -ve, then -a-b => -(a+b)
		 */
		
		else if(!a.isPositive && !b.isPositive) {
			res = addUtil(a, b);
			res.isPositive = false;
			
			return res;
		}
		
		/**
		 * If a is -ve and b is +ve, then -a + b
		 * 
		 */
		
		else if(!a.isPositive && b.isPositive) {
			if(isLesserThan(a, b)) {
				res = subtractUtil(b, a);
				res.isPositive = true;
			}
			else {
				res = subtractUtil(a, b);
				res.isPositive = false;
			}
			return res;
		}
		
		/**
		 * 
		 * If a is +ve and b is -ve, then a - b
		 */
		
		else {
			if(isLesserThan(b, a)){
				res = subtractUtil(a, b);
				res.isPositive = true;
			}
			else {
				res = subtractUtil(b, a);
				res.isPositive = false;
			}
			return res;
		}
	}
	
	public static BigNumber trim(BigNumber num) {
	
		for(int i = num.digits.size() - 1; i > 0; i--) {
			if(num.digits.get(i) == 0) num.digits.remove(i);
			else break;
		}
		
		return num;
	}
	
	static void padZeroes(BigNumber bigNumber, int len, boolean front) {
		ArrayList<Long> list = bigNumber.digits;
		for(int i = 0; i < len; i++) {
			if(!front) list.add(0L);
			else list.add(0, 0L);
		}
	}
	
	static BigNumber multiplySingleBit(BigNumber x, BigNumber y) {
		ArrayList<Long> xList = x.digits;
		ArrayList<Long> yList = y.digits;
		
		return new BigNumber(xList.get(0) * yList.get(0));
	}
	
	/**
	 * Karatsuba Multiplication
	 * 
	 * @param num1
	 * @param num2
	 * @return
	 */
	static BigNumber multiply(BigNumber num1, BigNumber num2) {
		
		int len1 = num1.digits.size();
		int len2 = num2.digits.size();
		
		int maxLen = Math.max(len1,  len2);
		
		//base conditions
		if(maxLen == 0) {
			return new BigNumber(0L);
		}
		
		if(maxLen == 1) {
			return multiplySingleBit(num1, num2);
		}
		
		if(len1 < len2) {
			padZeroes(num1, len2 - len1, false);
		} else if(len1 > len2) {
			padZeroes(num2, len1 - len2, false);
		}
		
		int left = (maxLen) / 2;
		
		/***
		 * num1 = a1a2
		 * num2 = b1b2
		 * 
		 * sum1 = (a1 + a2)
		 * sum2 = (b1 + b2)
		 * 
		 * sum = sum1 * sum2
		 * 
		 * subtract = sum - (a2b2 + a1b1)
		 * 
		 * product = a2b2 * (base ^ (2 * left)) + subtract * (base ^ left) + a1b1
		 * 
		 * 
		 */
		
		// split num1 into two halves 
		BigNumber a1 = num1.splitBigNumber(0, left);
		BigNumber a2 = num1.splitBigNumber(left, maxLen);
		
		// split num2 into two halves
		BigNumber b1 = num2.splitBigNumber(0, left);
		BigNumber b2 = num2.splitBigNumber(left, maxLen);
		
		BigNumber a1b1 = multiply(a1, b1);
		BigNumber a2b2 = multiply(a2, b2);
		
		BigNumber sum1 = addUtil(a1, a2);
		BigNumber sum2 = addUtil(b1, b2);
		BigNumber sum = multiply(sum1, sum2);
		
		BigNumber subtract = subtractUtil(sum, add(a2b2, a1b1));
		
		//padding zeroes at the front
		padZeroes(a2b2, 2 * left, true);
		padZeroes(subtract, left, true);
		
		BigNumber res1 = addUtil(a2b2, subtract);
		trim(res1);
		BigNumber res2 = addUtil(res1, a1b1);
		trim(res2);
		
		return res2; // product
	}
	
	/****
	 * High school multiplication technique.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	
	public static BigNumber product(BigNumber a, BigNumber b) {
		
		if(a == null || b == null) return null;
		
		int len1 = a.digits.size();
		int len2 = b.digits.size();
		
		int base = a.base;
		
		BigNumber res = new BigNumber(0L);
		for(int i = 0; i < len1 + len2 - 1; i++) {
			res.digits.add(0L);
		}
				
		for(int i = 0; i < len1; i++) {
			long carry = 0;
			for(int j = 0; j < len2; j++){
				long sum = res.digits.get(i + j) + a.digits.get(i) * b.digits.get(j) + carry;
				res.digits.set(i + j, sum % base);
				carry = sum / base;
			}
			if(carry > 0) 
				res.digits.set(i + len2, carry);
		}
		
		trim(res);
		
		res.isPositive = getSignForProductNDivide(a.isPositive, b.isPositive);
		
		return res;
	}
	
	public static BigNumber power(BigNumber a, long n) {
		if(n == 0)
			return new BigNumber(1L);

		if(n == 1)
			return a;

		BigNumber res = power(a, n / 2);

		if(n % 2 == 0){
			
			return product(res, res);
		}
		else {
			return product(a, product(res, res));
		}
	}
	
	public static BigNumber powerUtil(BigNumber a, BigNumber n) {
		if(isEqual(n, new BigNumber(0L)))
			return new BigNumber(1L);
		
		if(isEqual(n, new BigNumber(1L)))
			return a;
		
		
		BigNumber modRes = mod(n, new BigNumber(2L));
		BigNumber res = powerUtil(a, divisionBy2(n));
		
		if(isEqual(modRes, new BigNumber(0L))){
			return product(res, res);
		}
		else {
			return product(a, product(res, res));
		}
	}
	
	public static BigNumber power(BigNumber a, BigNumber n) {
		
		// Assume that a may be positive or negative. n is non-negative
		//base cases
		
		if(a == null || n == null) return null;
		
		boolean isPositive = true;
		
		if(a.isPositive == false) {
			BigNumber modResult = mod(n, new BigNumber(2L));
			if(!isEqual(modResult, new BigNumber(0L)))
				isPositive = false;
		}
		
		BigNumber res = powerUtil(a, n);
		
		trim(res);
		res.isPositive = isPositive;
		
		return res;
		
	}
	
	static BigNumber divisionBy2(BigNumber num) {
		BigNumber bigNum = new BigNumber();
		bigNum.isPositive = num.isPositive;
		Long carry = 0L;
		Long quotient = 0L;
		

		for (int i = num.digits.size() - 1; i >= 0; i--) {
			quotient = (num.digits.get(i) + carry * num.base) / 2;
			carry = (num.digits.get(i) % 2);
			bigNum.digits.add(0, quotient);

		}
		return trim(bigNum);
	}
	
	public static boolean isGreaterThan(BigNumber a, BigNumber b) {
		a = trim(a);
		b = trim(b);
		
		int len_a = a.digits.size();
		int len_b = b.digits.size();
		
		if(len_a > len_b) {
			return true;
		}
		else if(len_a < len_b) {
			return false;
		}
		else {
			for(int i = a.digits.size() - 1; i >= 0; i--) {
				if(a.digits.get(i) > b.digits.get(i))
					return true;
				else if(a.digits.get(i) < b.digits.get(i))
					return false;
			}
		}
		
		return false;
	}
	
	public static boolean isLesserThan(BigNumber a, BigNumber b) {
		a = trim(a);
		b = trim(b);
		
		int len_a = a.digits.size();
		int len_b = b.digits.size();
		
		if(len_a > len_b) {
			return false;
		}
		else if(len_a < len_b) {
			return true;
		}
		else {
			for(int i = a.digits.size() - 1; i >= 0; i--) {
				if(a.digits.get(i) < b.digits.get(i))
					return true;
				else if(a.digits.get(i) > b.digits.get(i))
					return false;
			}
		}
		
		return false;
	}
	
	public static boolean isEqual(BigNumber a, BigNumber b) {
		a = trim(a);
		b = trim(b);
		
		int len_a = a.digits.size();
		int len_b = b.digits.size();
		
		if(len_a > len_b) {
			return false;
		}
		else if(len_a < len_b) {
			return false;
		}
		else {
			for(int i = a.digits.size() - 1; i >= 0; i--) {
				if(a.digits.get(i) != b.digits.get(i))
					return false;
			}
		}
		
		return true;
	}
	
	public static boolean getSignForProductNDivide(boolean a, boolean b) {
		if(a && b) return true;
		
		else if(!a && !b) return true;
		
		else return false;
	}
	
	public static BigNumber divide(BigNumber a, BigNumber b) {
		
		//a is the BigNumber
		//b is the divisor
		
		//base cases
		if(a == null || b == null) return null;
		
		//get the sign for the result
		boolean isPositive = getSignForProductNDivide(a.isPositive, b.isPositive);
		
		if(b.digits.size() == 1 && b.digits.get(0) == 0L) throw new RuntimeException("Divide by Zero Error");
		
		if(b.digits.size() == 1 && b.digits.get(0) == 1L) {
			trim(a);
			a.isPositive = isPositive;
			return a;
		}
		
		if(isLesserThan(a, b)) return new BigNumber(0L);
		
		/***
		 * 
		 * Division algorithm using binary search
		 * 
		 * Q = N / D
		 * 
		 * Range of Q is from 0 to N.
		 * 
		 * Q/2 * D = N return Q/2; // quotient
		 * 
		 * if Q/2 * D < N
		 * 		if Q/2 + 1 * D > N return Q/2;
		 * 		else binary_search (Q/2 + 1, N);
		 * 
		 * else if Q/2 * D > N
		 * 		if(Q/2 - 1 * D < N return Q/2 - 1;
		 * 		else binary_search(start, Q/2 - 1);
		 * 
		 */
		
		BigNumber start = new BigNumber(0L);
		BigNumber end = a;
		
		while(isLesserThan(start, end) || isEqual(start, end)) {
			BigNumber mid = divisionBy2(addUtil(start, end));
			
			BigNumber prodMid_Div = product(mid, b);
			
			if(isEqual(a, prodMid_Div)) {
				trim(mid);
				mid.isPositive = isPositive;
				
				return mid;
			}
			
			else if(isLesserThan(prodMid_Div, a)) {
				BigNumber midPlusOne = addUtil(mid, new BigNumber(1L));
				BigNumber prodMidPlusOne_Div = product(midPlusOne, b);
				
				if(isGreaterThan(prodMidPlusOne_Div, a)) {
					trim(mid);
					mid.isPositive = isPositive;
					
					return mid;
				}
				else {
					start = midPlusOne;
				}
			}
			
			else {
				BigNumber midMinusOne = subtractUtil(mid, new BigNumber(1L));
				BigNumber prodMidMinusOne_Div = product(midMinusOne, b);
				
				if(isLesserThan(prodMidMinusOne_Div, a) || isEqual(prodMidMinusOne_Div, a)) {
					trim(midMinusOne);
					midMinusOne.isPositive = isPositive;
					
					return midMinusOne;
				}
				
				else {
					end = midMinusOne;
				}
			}
		}
		
		return new BigNumber(-1L);
	}
	
	public static BigNumber mod(BigNumber a, BigNumber b) {
		
		//Assuming a is non-negative and b > 0.
		
		//base cases
		if(a == null || b == null) return null;
		
		if(isLesserThan(a, b)) return a;
		
		if(isEqual(b, new BigNumber(1L))) return new BigNumber(0L);
		
		BigNumber quotient = divide(a, b);
		BigNumber prodQ_D = product(quotient, b);
		
		BigNumber modResult = subtractUtil(a, prodQ_D);
		
		trim(modResult);
		modResult.isPositive = a.isPositive;
		
		return modResult;
	}
	
	public static BigNumber squareRoot(BigNumber a) {
		
		// Assuming a is non - negative
		
		// base cases
		if(a == null) return null;
		
		if(isEqual(a, new BigNumber(0L)) || isEqual(a, new BigNumber(1L))) return a;
		
		// Use binary search to retrieve the square root of truncated
		
		BigNumber start = new BigNumber(1L);
		BigNumber end = a;
		BigNumber res = null;
		
		while(isLesserThan(start, end) || isEqual(start, end)) {
			BigNumber mid = divisionBy2(add(start, end));
			
			BigNumber midSquare = product(mid, mid);
			
			if(isEqual(midSquare, a)) return mid;
			
			else if(isLesserThan(midSquare, a)) {
				start = add(mid, new BigNumber(1L));
				res = mid; // If not a perfect square, find the truncated version of the square root. 
			}
			
			else {
				end = subtract(mid, new BigNumber(1L));
			}
		}
		
		trim(res);
		res.isPositive = a.isPositive;
		
		return res;
	}
	
	public static BigNumber factorial(BigNumber num) {
		
		if(num == null) return null;
		
		if(isEqual(num, new BigNumber(0L))) return new BigNumber(1L);
		
		if(isEqual(num, new BigNumber(1L))) return new BigNumber(1L);
		
		BigNumber zero = new BigNumber(0L);
		BigNumber one = new BigNumber(1L);
		BigNumber result = new BigNumber(1L);
		BigNumber temp = new BigNumber(1L);
		
		while(!isGreaterThan(temp, num)) {
			  result = product(result,temp);
			  temp = addUtil(temp, one);
		}
		
		trim(result);
		result.isPositive = num.isPositive;
		
		return result;
	}
	
	public static void main(String[] args)
	{
		BigNumber a = new BigNumber("5");
	//	BigNumber b = new BigNumber("-12345678999876543");
		
		System.out.println(a.digits);
		System.out.println(a);
		
		//System.out.println(test);

	}

}
