package cs362.dominion.Exceptions;

public class ConfiguationException extends Exception {
    /**
	 * Represents an error with the configuration
	 */
	private static final long serialVersionUID = 1L;
	public ConfiguationException(String message) {
        super(message);
    }
}