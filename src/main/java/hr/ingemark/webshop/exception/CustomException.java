package hr.ingemark.webshop.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int code;

	public CustomException(int code, String pMessage) {
		super(pMessage);
		this.code = code;
	}

}
