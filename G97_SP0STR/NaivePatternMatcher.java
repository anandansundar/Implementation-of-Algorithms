import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NaivePatternMatcher {
	
	public int stringMatcher(String text, String pattern) {
		int textLen = text.length();
		int patternLen = pattern.length();
		
		for(int i = 0; i <= textLen - patternLen; i++) {
			
			int idx;
			
			for(idx = 0; idx < patternLen; idx++) {
				if(text.charAt(i + idx) != pattern.charAt(idx)) {
					break;
				}
			}
			
			if(idx == patternLen) {
				return i;
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
		NaivePatternMatcher naivePatternMatcher = new NaivePatternMatcher();
		
		String text = naivePatternMatcher.readFile("input.txt");
		String pattern = "BACD";
		
		Timer t = new Timer();
		t.start();
		int validShift = naivePatternMatcher.stringMatcher(text, pattern);
		System.out.println("valid shift: " + validShift);
		t.end();
		System.out.println(t);
	}

}
