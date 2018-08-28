package com.timerecordersystem.resource;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Classe auxiliar para o tempo de descanso.
 * @author ezequias.oliveira
 *
 */
public class BreakTimeResource {
	
	/**
	 * Dia trabalhado.
	 */
	private LocalDate moment;
	/**
	 * Horas Trabalhadas.
	 */
	private LocalTime workedHours;
	/**
	 * Tempo de descanso. 
	 */
	private LocalTime breakHours;
	
	public BreakTimeResource() {
		super();
	}
	
	/**
	 * Retorna a data do dia trabalhado.
	 * @return {@link LocalDate}
	 */
	public LocalDate getMoment() {
		return moment;
	}
	/**
	 * Recebe o dia trabalhado.
	 * @param moment
	 */
	public void setMoment(LocalDate moment) {
		this.moment = moment;
	}
	/**
	 * Retorna as horas Trabalhadas.
	 * @return {@link LocalTime}
	 */
	public LocalTime getWorkedHours() {
		return workedHours;
	}
	/**
	 * Receber as horas Trabalhadas.
	 * @param workedHours
	 */
	public void setWorkedHours(LocalTime workedHours) {
		this.workedHours = workedHours;
	}
	/**
	 * Retorna o tempo de descanso.
	 * @return {@link LocalTime}
	 */
	public LocalTime getBreakHours() {
		return breakHours;
	}
	/**
	 * Recebe o tempo de descanso.
	 * @param breakHours
	 */
	public void setBreakHours(LocalTime breakHours) {
		this.breakHours = breakHours;
	}
	
	
}
