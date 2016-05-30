import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RabinKarp {
	
	private static final int prime = 97;
	
	public long getHashCode(String str, int len) {
		long hash = 0;
		
		for(int i = 0; i < len; i++) {
			hash += str.charAt(i) * Math.pow(prime, i);
		}
		
		return hash;
	}
	
	public boolean isEqual(String text, int tStart, int tEnd, String pattern, int pStart, int pEnd) {
		int tLen = tEnd - tStart;
		int pLen = pEnd - pStart;
		
		if(pLen != tLen) {
			return false;
		}
		
		while(tStart <= tEnd && pStart <= pEnd) {
			if(text.charAt(tStart) != pattern.charAt(pStart)) {
				return false;
			}
			
			tStart++;
			pStart++;
		}
		
		return true;
	}
	public int stringMatcher(String text, String pattern) {
		
		int textLen = text.length();
		int patternLen = pattern.length();
		
		// calculate the hash for the pattern
		long patternHashCode = getHashCode(pattern, patternLen);
		
		long textHashCode = getHashCode(text, patternLen);
		
		for(int i = 1; i <= textLen - patternLen; i++) {
			
			if(patternHashCode == textHashCode) {
				if(isEqual(text, i - 1, i + patternLen - 2, pattern, 0, patternLen - 1)) {
					return i - 1;
				}
			}
			
			// If not equal, re compute the hash value
			
			if(i <= textLen - patternLen) {
			
				long newStrHashCode = textHashCode - text.charAt(i - 1);
				newStrHashCode /= prime;
				newStrHashCode += text.charAt(i + patternLen - 1) * Math.pow(prime, patternLen - 1);
				textHashCode = newStrHashCode;
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
		
		String pattern = "bbz";
		
		RabinKarp rabinKarp = new RabinKarp();
		String text = rabinKarp.readFile("input.txt");
		Timer t = new Timer();
		t.start();
		int validShift = rabinKarp.stringMatcher(text, pattern);
		System.out.println("Valid shift: " + validShift);
		t.end();
		System.out.println(t);
	}

}
