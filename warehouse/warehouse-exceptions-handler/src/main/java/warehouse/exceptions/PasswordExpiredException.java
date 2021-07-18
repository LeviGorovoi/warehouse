package warehouse.exceptions;

public class PasswordExpiredException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PasswordExpiredException(String message) {
		super(message);
	}
}
