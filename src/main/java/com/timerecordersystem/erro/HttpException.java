package com.timerecordersystem.erro;

import org.springframework.http.HttpStatus;

/**
 * Exceção para as requisições http.
 * 
 * @author ezequias.oliveira
 *
 */
public class HttpException extends Exception {
	
	private static final long serialVersionUID = 1186508650723593539L;
	
	private HttpStatus httpStatus;

	public HttpException(String mensage, HttpStatus httpStatus) {
		super(mensage);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	
}
