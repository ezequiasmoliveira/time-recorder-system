package com.timerecordersystem.erro;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = -1205199260716628380L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
