package cs362.dominion.Exceptions;

public class InvalidOptionException extends Exception {
    /**
	 * Represents an error with a user option selection
	 */
	private static final long serialVersionUID = 1L;

	public InvalidOptionException(String message) {
        super(message);
    }
}
