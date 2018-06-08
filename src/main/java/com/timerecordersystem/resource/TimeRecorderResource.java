package com.timerecordersystem.resource;

import java.sql.Date;

/**
 * Classe auxiliar para registro do ponto.
 * 
 * @author ezequias.oliveira
 *
 */
public class TimeRecorderResource {

	private Date momment;
	private Long idEmployee;
	
	public TimeRecorderResource() {
		super();
	}
	
	public TimeRecorderResource(Date momment, Long idEmployee) {
		super();
		this.momment = momment;
		this.idEmployee = idEmployee;
	}

	public Date getMomment() {
		return momment;
	}
	public void setMomment(Date momment) {
		this.momment = momment;
	}
	public Long getIdEmployee() {
		return idEmployee;
	}
	public void setIdEmployee(Long idEmployee) {
		this.idEmployee = idEmployee;
	}
	
	
}
