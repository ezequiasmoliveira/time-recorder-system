package com.timerecordersystem.resource;

import java.time.LocalDateTime;

import com.timerecordersystem.model.TimeRecorder;

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
	
	public RecordResource(final TimeRecorder entity) {
		this.setMoment(entity.getMoment());
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
