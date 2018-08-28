package com.timerecordersystem.resource;

import java.time.LocalDateTime;

/**
 * Classe auxiliar para o registro. 
 * 
 * @author ezequias.oliveira
 *
 */
public class RecordResource {
	
	/**
	 * Data da batida do ponto.
	 */
	private LocalDateTime moment;

	public RecordResource() {
		super();
	}
	
	/**
	 * Retorna a data batida do ponto.
	 * @return {@link LocalDateTime}
	 */
	public LocalDateTime getMoment() {
		return moment;
	}
	/**
	 * Recebe a data batida do ponto.
	 * @param moment
	 */
	public void setMoment(LocalDateTime moment) {
		this.moment = moment;
	}
	

}
