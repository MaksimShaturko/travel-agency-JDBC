package by.epam.shaturko.service.exception;

public class ServiceException extends Exception {
	private static final long serialVersionUID = -997217513610804759L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Exception e) {
		super(e);
	}

	public ServiceException(String message, Exception e) {
		super(message, e);
	}

}
