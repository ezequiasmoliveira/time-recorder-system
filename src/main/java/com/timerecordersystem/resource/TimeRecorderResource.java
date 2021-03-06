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
	private LocalDateTime moment;
	/**
	 * PIS do empregado.
	 */
	private Long pis;
	
	public TimeRecorderResource() {
		super();
	}
	
	/**
	 * Retorna a data da batida do ponto.
	 * @return {@link LocalDateTime}
	 */
	public LocalDateTime getMoment() {
		return moment;
	}
	/**
	 * Recebe a data da batida do ponto.
	 * @param moment
	 */
	public void setMoment(LocalDateTime moment) {
		this.moment = moment;
	}
	/**
	 * Retorna o PIS do empregado.
	 * @return {@link Long} 
	 */
	public Long getPis() {
		return pis;
	}
	/**
	 * Recebe o PIS do empregado.
	 * @param pis
	 */
	public void setPis(Long pis) {
		this.pis = pis;
	}
	
	
}
