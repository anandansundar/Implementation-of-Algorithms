public class Fibonacci {
	
	/**
	 * @param n - nth term in fibonacci series
	 * @param p - user defined value used for modulo
	 */
	
	//A Dynamic programming program to compute nth fibonacci number.
	
	public static long linearFibonacci(long n, long p){
	
		long [] fibo = new long[(int)n + 1];
		fibo[0] = 0;
		fibo[1] = 1;
		
		for (int i = 2; i <= n; i++){
			fibo[i] = (fibo[i-1] + fibo [i-2]) % p; 
		}
		
		return fibo[(int)n];
	}
		
	/**
	 * Multiplication of the computed matrix F with the matrix M 
	 * @param F - Matrix for computing fibonacci values 
	 * @param M - Donald E. Knuth Matrix for multiplication
	 * @param p - user defined value used for modulo
	 */
	
	static void product(long F[][], long M[][], long p)
	{
		long x =  (F[0][0]*M[0][0] + F[0][1]*M[1][0]) % p;
		long y =  (F[0][0]*M[0][1] + F[0][1]*M[1][1]) % p;
		long z =  (F[1][0]*M[0][0] + F[1][1]*M[1][0]) % p;
		long w =  (F[1][0]*M[0][1] + F[1][1]*M[1][1]) % p;

		F[0][0] = x;
		F[0][1] = y;
		F[1][0] = z;
		F[1][1] = w;
	}


	/**
	 * Computing power to find the fibonacci number. Using Recursive approach with memoization to get O(log n) time.
	 */

	static void power_optimised(long F[][], long n, long p) {

		if (n == 0 || n == 1)
			return;

		long M[][] = {{1,1},{1,0}};
		power_optimised(F, n/2, p);
		product(F, F, p);

		if (n % 2 != 0)
			product(F, M, p);

	}
	
	public static long logFibonacci(long n, long p) {

		long [][] F = {{1,1},{1,0}};
		
		if (n == 0)
			return 0;
	
		power_optimised(F, n-1, p);

		return F[0][0];
	}
	
	public static void main(String[] args) {
	
		long startReadTime = System.nanoTime();
		
		long result1 = linearFibonacci(25, 999953);
		
		long totalLoadTime = System.nanoTime() - startReadTime;

		System.out.println(totalLoadTime + " nanoseconds");

		System.out.println("Result in linearFibonacci: " +result1	);
		
		long startReadTime1 = System.nanoTime();
		
		long result2 = logFibonacci(25, 999953);
		
		long totalLoadTime1 = System.nanoTime() - startReadTime1;

		System.out.println(totalLoadTime1 + " nanoseconds");

		System.out.println("Result in LogFibonacci: " +result2);
		
	}

}
