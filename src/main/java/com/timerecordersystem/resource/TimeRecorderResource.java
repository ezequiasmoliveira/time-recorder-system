package com.timerecordersystem.resource;

import java.time.LocalDateTime;

/**
 * Classe auxiliar para registro do ponto.
 * 
 * @author ezequias.oliveira
 *
 */
public class TimeRecorderResource {

	/**
	 * Data da batida do ponto.
	 */
	private LocalDateTime momment;
	/**
	 * PIS do empregado.
	 */
	private Long pis;
	
	public TimeRecorderResource() {
		super();
	}
	
	public LocalDateTime getMomment() {
		return momment;
	}
	public void setMomment(LocalDateTime momment) {
		this.momment = momment;
	}
	public Long getPis() {
		return pis;
	}
	public void setPis(Long pis) {
		this.pis = pis;
	}
	
	
}
