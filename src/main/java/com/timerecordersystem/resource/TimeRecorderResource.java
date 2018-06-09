package com.timerecordersystem.resource;

import java.time.LocalDateTime;

/**
 * Classe auxiliar para registro do ponto.
 * 
 * @author ezequias.oliveira
 *
 */
public class TimeRecorderResource {

	private LocalDateTime momment;
	private Long idEmployee;
	
	public TimeRecorderResource() {
		super();
	}
	
	public LocalDateTime getMomment() {
		return momment;
	}
	public void setMomment(LocalDateTime momment) {
		this.momment = momment;
	}
	public Long getIdEmployee() {
		return idEmployee;
	}
	public void setIdEmployee(Long idEmployee) {
		this.idEmployee = idEmployee;
	}
	
	
}
