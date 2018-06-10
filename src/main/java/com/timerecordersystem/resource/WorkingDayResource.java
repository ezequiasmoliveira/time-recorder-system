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
	private LocalDate momment;
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
		this.setMomment(entity.getMomment());
		
		List<RecordResource> recordsResource = new ArrayList<>();
		for (TimeRecorder record : entity.getRecords()) {
			
			recordsResource.add(new RecordResource(record));
		}
		this.setRecords(recordsResource);
	}
	
	public LocalDate getMomment() {
		return momment;
	}
	public void setMomment(LocalDate momment) {
		this.momment = momment;
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
