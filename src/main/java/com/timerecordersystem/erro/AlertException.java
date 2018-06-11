package com.timerecordersystem.erro;

/**
 * Exception de alerta.
 * 
 * @author ezequias.oliveira
 *
 */
public class AlertException extends BusinessException{

	private static final long serialVersionUID = 4759074979929372946L;

	public AlertException(String mensagem) {
		super(mensagem);
	}
}
