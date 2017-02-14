package cs362.dominion.Exceptions;

public class InvalidSelectionException extends Exception {
    /**
	 * Represents an error with a selected card.
	 */
	private static final long serialVersionUID = 1L;

	public InvalidSelectionException(String message) {
        super(message);
    }
}
