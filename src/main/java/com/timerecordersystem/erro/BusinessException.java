package com.timerecordersystem.erro;
/**
 * Exceção para a regra de negócio.
 * 
 * @author ezequias.oliveira
 *
 */
public class BusinessException extends Exception{

	private static final long serialVersionUID = -9177492002307208445L;
	private String exception;
	
	public BusinessException(String mensagem) {
		super(mensagem);
		this.exception = this.getClass().getSimpleName();
	}

	public String getException() {
		return exception;
	}

}
