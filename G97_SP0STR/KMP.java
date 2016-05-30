import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class KMP {
	
	private int[] computePrefix(String pattern) {
		
		int patternLen = pattern.length();
		
		int[] prefix;
		prefix = new int[patternLen];
		
		prefix[0] = 0;
		
		int k = 0;
		
		for(int q = 1; q < patternLen;) {
			if(pattern.charAt(k) == pattern.charAt(q)) {
				prefix[q] = k + 1;
				k++;
				q++;
			} else {
				if(k != 0) {
					k = prefix[k - 1];
				} else {
					prefix[q] = 0;
					q++;
				}
			}
		}
		
		/*for(int i = 0; i < prefix.length; i++) {
			System.out.print(prefix[i] + " ");
		}*/
		
		return prefix;
	}
	
	public int stringMatcher(String text, String pattern) {
		
		// computing prefix array
		int prefix[] = computePrefix(pattern);		
		
		int textLen = text.length();
		int patternLen = pattern.length();
		
		int tIdx = 0;
		int pIdx = 0;
		
		while(tIdx < textLen && pIdx < patternLen) {
			if(text.charAt(tIdx) == pattern.charAt(pIdx)) {
				tIdx++;
				pIdx++;
			} else {
				if(pIdx != 0) {
					pIdx = prefix[pIdx - 1];
				} else {
					tIdx++;
				}
			}
			
			if(pIdx == patternLen) {
				return tIdx - pIdx;
			}
		}
		
		return -1;
	}
	public String readFile(String file) throws IOException {
	    BufferedReader reader = new BufferedReader(new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    try {
	        while((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	            stringBuilder.append(ls);
	        }

	        return stringBuilder.toString();
	    } finally {
	        reader.close();
	    }
	}
	public static void main(String[] args) throws IOException {
		KMP kmp = new KMP();
		
		String text = kmp.readFile("input.txt");
		
		//String text = "  ";
		
		String pattern = "adasfsadfsfsd";
		Timer t = new Timer();
		t.start();
		int validShift = kmp.stringMatcher(text, pattern);
		System.out.println("Valid shift: " + validShift);
		t.end();
		System.out.println(t);
	}

}
