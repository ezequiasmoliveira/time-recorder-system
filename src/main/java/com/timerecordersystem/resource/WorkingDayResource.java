package com.timerecordersystem.resource;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe auxiliar para o dia trabalhado.
 * 
 * @author ezequias.oliveira
 *
 */
public class WorkingDayResource {

	/**
	 * Data de trabalho.
	 */
	private LocalDate moment;
	/**
	 * Registros das batidas do ponto.
	 */
	private List<RecordResource> records;
	/**
	 * Intervalo de descanso
	 */
	private Long breakTime;
	
	public WorkingDayResource() {
		super();
	}
	
	public LocalDate getMoment() {
		return moment;
	}
	public void setMoment(LocalDate moment) {
		this.moment = moment;
	}
	public List<RecordResource> getRecords() {
		return records;
	}
	public void setRecords(List<RecordResource> records) {
		this.records = records;
	}
	public Long getBreakTime() {
		return breakTime;
	}
	public void setBreakTime(Long breakTime) {
		this.breakTime = breakTime;
	}
	
}
