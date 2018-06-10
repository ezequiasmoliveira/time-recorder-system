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
	private LocalDateTime momment;

	public RecordResource() {
		super();
	}
	
	public RecordResource(final TimeRecorder entity) {
		this.setMomment(entity.getMomment());
	}
	/**
	 * Retorna a data batida do ponto.
	 * @return {@link LocalDateTime}
	 */
	public LocalDateTime getMomment() {
		return momment;
	}
	/**
	 * Recebe a data batida do ponto.
	 * @param momment
	 */
	public void setMomment(LocalDateTime momment) {
		this.momment = momment;
	}
	

}
