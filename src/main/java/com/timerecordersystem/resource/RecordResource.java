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
	 * Data que bateu o ponto.
	 */
	private LocalDateTime momment;

	public RecordResource() {
		super();
	}
	
	public RecordResource(final TimeRecorder entity) {
		this.setMomment(entity.getMomment());
	}

	public LocalDateTime getMomment() {
		return momment;
	}

	public void setMomment(LocalDateTime momment) {
		this.momment = momment;
	}
	

}
