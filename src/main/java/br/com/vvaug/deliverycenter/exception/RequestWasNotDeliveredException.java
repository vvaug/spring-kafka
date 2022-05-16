package br.com.vvaug.deliverycenter.exception;

public class RequestWasNotDeliveredException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RequestWasNotDeliveredException(String msg) {
		super(msg);
	}
}