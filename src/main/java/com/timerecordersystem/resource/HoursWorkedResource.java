package com.timerecordersystem.resource;

/**
 * Classe auxiliar para o acumulado das horas trabalhadas. 
 * 
 * @author ezequias.oliveira
 *
 */
public class HoursWorkedResource {

	/**
	 * Acumulado de horas no dia.
	 */
	private Long hoursWorkedDay;
	/**
	 * Acumulado de horas no mês.
	 */
	private Long hoursWorkedMonth;
	
	public HoursWorkedResource() {
		super();
	}

	/**
	 * Retorna o acumulado de horas no dia.
	 * @return {@link Long}
	 */
	public Long getHoursWorkedDay() {
		return hoursWorkedDay;
	}
	/**
	 * Recebe o acumulado de horas no dia.
	 * @param hoursWorkedDay
	 */
	public void setHoursWorkedDay(Long hoursWorkedDay) {
		this.hoursWorkedDay = hoursWorkedDay;
	}
	/**
	 * Retorna o acumulado de horas no mês.
	 * @return {@link Long}
	 */
	public Long getHoursWorkedMonth() {
		return hoursWorkedMonth;
	}
	/**
	 * Recebe o acumulado de horas no mês.
	 * @param hoursWorkedDay
	 */
	public void setHoursWorkedMonth(Long hoursWorkedMonth) {
		this.hoursWorkedMonth = hoursWorkedMonth;
	}
	
	
}
