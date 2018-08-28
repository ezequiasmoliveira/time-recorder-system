package com.timerecordersystem.resource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.timerecordersystem.model.TimeRecorder;
import com.timerecordersystem.model.Worked;

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
	
	public WorkingDayResource(final Worked entity) {
		this.setMoment(entity.getMoment());
		
		List<RecordResource> recordsResource = new ArrayList<>();
		for (TimeRecorder record : entity.getRecords()) {
			
			recordsResource.add(new RecordResource(record));
		}
		this.setRecords(recordsResource);
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
