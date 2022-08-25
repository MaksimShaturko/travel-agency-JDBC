package by.epam.shaturko.dao.connection_pool;

public class ConnectionPoolException extends Exception{
	private static final long serialVersionUID = -1704382589656850238L;

	public ConnectionPoolException() {
		super();
	}

	public ConnectionPoolException(String message) {
		super(message);
	}

	public ConnectionPoolException(String message, Exception e) {
		super(message, e);
	}

	public ConnectionPoolException(Exception e) {
		super(e);
	}
}
