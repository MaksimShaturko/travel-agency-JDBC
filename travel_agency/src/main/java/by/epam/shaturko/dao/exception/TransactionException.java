package by.epam.shaturko.dao.exception;

/**
 * The TransactionException class represents an exception that may be thrown
 * with transaction operations.
 *
 * @author Maksim Shaturko
 * @version 1.0
 */
public class TransactionException extends Exception{
	private static final long serialVersionUID = -5972137302705529954L;

	public TransactionException() {
		super();
	}

	public TransactionException(String message) {
		super(message);
	}

	public TransactionException(String message, Exception e) {
		super(message, e);
	}

	public TransactionException(Exception e) {
		super(e);
	}
}


