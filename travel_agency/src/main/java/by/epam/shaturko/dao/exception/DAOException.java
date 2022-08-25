package by.epam.shaturko.dao.exception;

/**
 * The DAOException class represents an exception that may be thrown
 * on the DAO layer
 *
 * @author Maksim Shaturko
 * @version 1.0
 */
public class DAOException extends Exception {
	private static final long serialVersionUID = -8808984565239045929L;

	public DAOException() {
		super();
	}
	
	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(Exception e) {
		super(e);
	}

	public DAOException(String message, Exception e) {
		super(message, e);
	}

}
