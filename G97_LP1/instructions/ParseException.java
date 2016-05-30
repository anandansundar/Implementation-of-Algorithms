package instructions;

public class ParseException extends Exception {

	private static final long serialVersionUID = 6553898550417681461L;
	
	public ParseException(String message) {
		super("Unable to parse the command:" + message);

	}

}
