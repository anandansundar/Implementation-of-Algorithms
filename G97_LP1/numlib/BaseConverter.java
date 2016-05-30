package numlib;

import java.util.ArrayList;
import java.util.Iterator;

public class BaseConverter {

	ArrayList<Long> digits;
	boolean isPositive;
	int base = 10;
	public static long count=0;

	BaseConverter() {
		count++;
		digits = new ArrayList<>();
	}

	BaseConverter(int base) {
		digits = new ArrayList<Long>();
		isPositive = true;
		this.base = base;
		count++;
	}

	BaseConverter(String s) {
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
	}

	BaseConverter(Long num) {
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

	public BaseConverter baseConvertTo10 (BaseConverter a, BaseConverter base){

		BaseConverter result = new BaseConverter(0L);
		BaseConverter mulresult;
		BaseConverter powerResult;
		Long l;
		int j = 0;
		
		while (j < a.digits.size()){

			powerResult = power(base, j);
			l = a.digits.get(j);
			BaseConverter mul = new BaseConverter(l);
			mulresult = multiply(mul, powerResult);  
			result = result.add(result, mulresult);
			j++;

		}

		return result;

	}

	public BaseConverter baseConvertFrom10 (BaseConverter a, BaseConverter base){

		BaseConverter result = new BaseConverter();

		BaseConverter quotient = divide(a, base);

		BaseConverter remainder = mod(a, base);

		BaseConverter temp;

		//BigNumber zero = new BigNumber(0L);

		Long l;

		while (isGreaterThan(quotient , base)){	

			l = Long.parseLong(remainder.toString());

			result.digits.add(l);

			temp = divide(quotient, base);

			remainder = mod(quotient, base);

			quotient = temp;	

		}

		l = Long.parseLong(remainder.toString());
		result.digits.add(l);

		l = Long.parseLong(quotient.toString());
		result.digits.add(l);

		result.isPositive = a.isPositive;
		
		return result;
	}

	@Override
	public String toString() {
		String num = "";
		boolean firstNonZeroNumberEncountered = false;
		if(isPositive == false)
		{
			num += "-";
		}
		for(int i = digits.size() - 1; i >= 0; i--) {
			if(digits.get(i) == 0 && firstNonZeroNumberEncountered == false) {
				continue;
			}
			else {
				firstNonZeroNumberEncountered=true;
			}
			num += digits.get(i)+ "";
		}

		if (digits.size() == 1 && digits.get(0) == 0){
			num = "0";
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

	public BaseConverter splitBigNumber(int fromIndex, int toIndex) {
		BaseConverter res = new BaseConverter(this.base);

		//System.out.println("#DEBUG split : " + this.digits);

		for(int i = fromIndex; i < toIndex; i++) {
			res.digits.add(this.digits.get(i));
		}
		res.isPositive = this.isPositive;

		return res;
	}

	public BaseConverter add(BaseConverter num1, BaseConverter num2) {
		BaseConverter res = new BaseConverter(num1.base);
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

	public BaseConverter subtract(BaseConverter num1, BaseConverter num2) {
		BaseConverter res = new BaseConverter(num1.base);
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

	public BaseConverter trim(BaseConverter num) {

		for(int i = num.digits.size() - 1; i > 0; i--) {
			if(num.digits.get(i) == 0) num.digits.remove(i);
			else break;
		}

		return num;
	}

	void padZeroes(BaseConverter bigNumber, int len, boolean front) {
		ArrayList<Long> list = bigNumber.digits;
		for(int i = 0; i < len; i++) {
			if(!front) list.add(0L);
			else list.add(0, 0L);
		}
	}

	public BaseConverter multiplySingleBit(BaseConverter x, BaseConverter y) {
		ArrayList<Long> xList = x.digits;
		ArrayList<Long> yList = y.digits;

		return new BaseConverter(xList.get(0) * yList.get(0));
	}

	public BaseConverter multiply(BaseConverter num1, BaseConverter num2) {

		int len1 = num1.digits.size();
		int len2 = num2.digits.size();
		int base = num1.base;

		int maxLen = Math.max(len1,  len2);

		//base conditions
		if(maxLen == 0) {
			return new BaseConverter(0L);
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
		BaseConverter a1 = num1.splitBigNumber(0, left);
		BaseConverter a2 = num1.splitBigNumber(left, maxLen);

		//		System.out.println("# DEBUG a1 " + a1.digits);
		//		System.out.println("# DEBUG a2 " + a2.digits);

		// split num2 into two halves
		BaseConverter b1 = num2.splitBigNumber(0, left);
		BaseConverter b2 = num2.splitBigNumber(left, maxLen);

		//		System.out.println("# DEBUG b1 " + b1.digits);
		//		System.out.println("# DEBUG b2 " + b2.digits);

		BaseConverter a1b1 = multiply(a1, b1);
		BaseConverter a2b2 = multiply(a2, b2);

		BaseConverter sum1 = add(a1, a2);
		BaseConverter sum2 = add(b1, b2);
		BaseConverter sum = multiply(sum1, sum2);

		BaseConverter subtract = subtract(sum, add(a2b2, a1b1));

		//padding zeroes at the front
		padZeroes(a2b2, 2 * left, true);
		padZeroes(subtract, left, true);

		//		System.out.println("# DEBUG a2b2 " + a2b2.digits);
		//		System.out.println("# DEBUG subtract " + subtract.digits);
		//		System.out.println("# DEBUG a1b1 " + a1b1.digits);

		BaseConverter res1 = add(a2b2, subtract);
		trim(res1);
		BaseConverter res2 = add(res1, a1b1);
		trim(res2);
		//		System.out.println("#DEBUG res2 " + res2.digits);

		return res2; // product
	}

	public BaseConverter product(BaseConverter a, BaseConverter b) {
		BaseConverter res = multiply(a, b);
		trim(res);

		return res;
	}

	public BaseConverter power(BaseConverter a, long n) {
		if(n == 0)
			return new BaseConverter(1L);

		if(n == 1)
			return a;

		BaseConverter res = power(a, n / 2);

		if(n % 2 == 0){
			
			return product(res, res);
		}
		else {
			return product(a, product(res, res));
		}
	}

	BaseConverter divisionBy2(BaseConverter num) {
		BaseConverter bigNum = new BaseConverter(num.base);
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

	boolean isGreaterThan(BaseConverter a, BaseConverter b) {
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

	boolean isLesserThan(BaseConverter a, BaseConverter b) {
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

	boolean isEqual(BaseConverter a, BaseConverter b) {
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

	BaseConverter divide(BaseConverter a, BaseConverter b) {

		//a is the BigNumber
		//b is the divisor

		if(b.digits.size() == 1 && b.digits.get(0) == 0L) throw new RuntimeException("Divide by Zero Error");

		if(b.digits.size() == 1 && b.digits.get(0) == 1L) return a;

		if(isLesserThan(a, b)) return new BaseConverter(0L);

		/***
		 * 
		 * Binary search algorithm
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

		BaseConverter start = new BaseConverter(0L);
		BaseConverter end = a;

		while(isLesserThan(start, end) || isEqual(start, end)) {
			BaseConverter mid = divisionBy2(add(start, end));

			//			System.out.println("#DEBUG mid " + mid.toString());

			BaseConverter prodMid_Div = multiply(mid, b);

			//			System.out.println("#DEBUG prodMid_Div " + prodMid_Div.toString());
			//			System.out.println("#DEBUG isEqual " + isEqual(a, prodMid_Div));
			//			System.out.println("#DEBUG isEqual " + isLesserThan(prodMid_Div, a));

			if(isEqual(a, prodMid_Div)) return mid;

			else if(isLesserThan(prodMid_Div, a)) {
				BaseConverter midPlusOne = add(mid, new BaseConverter(1L));
				BaseConverter prodMidPlusOne_Div = multiply(midPlusOne, b);

				if(isGreaterThan(prodMidPlusOne_Div, a)) {
					return mid;
				}
				else {
					start = midPlusOne;
				}
			}

			else {
				BaseConverter midMinusOne = subtract(mid, new BaseConverter(1L));
				BaseConverter prodMidMinusOne_Div = multiply(midMinusOne, b);

				//				System.out.println("#DEBUG prodMidMinusOne_Div " + prodMidMinusOne_Div);
				//				
				//				System.out.println("# DEBUG isLesserThan(prodMidMinusOne_Div, a) " + isLesserThan(prodMidMinusOne_Div, a));
				//				System.out.println("# DEBUG isEqual(prodMidMinusOne_Div, a) " + isEqual(prodMidMinusOne_Div, a));
				//				
				if(isLesserThan(prodMidMinusOne_Div, a) || isEqual(prodMidMinusOne_Div, a)) 
					return midMinusOne;

				else {
					end = midMinusOne;

					//					System.out.println("#DEBUG end" + end.toString());
				}
			}
		}

		return new BaseConverter(-1L);
	}

	public BaseConverter mod(BaseConverter a, BaseConverter b) {

		if(isLesserThan(a, b)) return a;

		if(isEqual(b, new BaseConverter(1L))) return new BaseConverter(0L);

		BaseConverter quotient = divide(a, b);

		BaseConverter prodQ_D = multiply(quotient, b);

		BaseConverter modResult = subtract(a, prodQ_D);

		return modResult;
	}

	public BaseConverter squareRoot(BaseConverter a) {

		// base cases
		if(isEqual(a, new BaseConverter(0L)) || isEqual(a, new BaseConverter(1L))) return a;

		// Use binary search to retrieve the square root of truncated

		BaseConverter start = new BaseConverter(1L);
		BaseConverter end = a;
		BaseConverter res = null;

		while(isLesserThan(start, end) || isEqual(start, end)) {
			BaseConverter mid = divisionBy2(add(start, end));

			BaseConverter midSquare = multiply(mid, mid);

			if(isEqual(midSquare, a)) return mid;

			else if(isLesserThan(midSquare, a)) {
				start = add(mid, new BaseConverter(1L));
				res = mid; // If not a perfect square, find the truncated version of the square root. 
			}

			else {
				end = subtract(mid, new BaseConverter(-1L));
			}
		}

		return res;
	}

	public BaseConverter factorial(BaseConverter num) {

		System.out.println("#DEBUG count " +BaseConverter.count);
		System.out.println("#DEBUG num " + num);

		if(num == null) return null;

		BaseConverter zero = new BaseConverter(0L);
		BaseConverter one = new BaseConverter(1L);
		BaseConverter result = new BaseConverter(1L);
		BaseConverter temp = new BaseConverter(1L);

		while(!isGreaterThan(temp, num)) {
			//			System.out.println("#DEBUG Before result  " + result+" -- "+BigNumber.count);
			//			System.out.println("DEBUG result size " + result.digits.size() + " temp size " + temp.digits.size());
			result = product(result,temp);
			//			  System.out.println("#DEBUG after resut  " + result+"--"+BigNumber.count);
			//			  System.out.println("DEBUG result size " + result.digits.size() + " temp size " + temp.digits.size());
			//			  System.out.println("#DEBUG result " + result);
			temp = add(temp, one);
		}
		return result;
	}
	
	public static BaseConverter convertFrombase10(BaseConverter a, BaseConverter b) {
		BaseConverter bignum = new BaseConverter();
		BaseConverter c;
		c = bignum.baseConvertFrom10(a, b);
		return c;
	}
	
	public static BaseConverter convertTobase10(BaseConverter a, BaseConverter b) {
		BaseConverter bignum = new BaseConverter();
		BaseConverter c;
		c = bignum.baseConvertTo10(a, b);
		return c;
	}


	public static void main(String[] args)
	{
		BaseConverter bignum = new BaseConverter();

		BaseConverter a = new BaseConverter("5");

		BaseConverter b = new BaseConverter("4");
		
		BaseConverter c=convertFrombase10(a, b);
		System.out.println(c.digits);
		
		BaseConverter d=convertTobase10(c, b);
		System.out.println(d.digits);
		
		
		System.out.println(c.digits);
		
		
		//System.out.println(bignum.baseConvertFrom10(a, b));
		/*c = bignum.baseConvertFrom10(a, b);
		System.out.println(c.digits  + ""+ c.base);
		BigNum d = a.baseConvertTo10(c, b);
		System.out.println(d.digits);*/
		//		long startTime = System.currentTimeMillis();
		//		BigNumber res = bignum.factorial(new BigNumber(100L));
		//		long endTime = System.currentTimeMillis();
		//		
		//		System.out.println("Time " + (endTime - startTime) + " ms");
		//		
		//		System.out.println(BigNumber.count);

	}

}
