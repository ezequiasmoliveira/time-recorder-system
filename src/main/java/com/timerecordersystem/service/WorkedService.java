package com.timerecordersystem.service;

import java.time.LocalDate;

import com.timerecordersystem.model.Employee;
import com.timerecordersystem.model.Worked;

/**
 * Interface que fornece os serviços para o dia trabalhado do funcionário.
 * 
 * @author ezequias.oliveira
 *
 */
public interface WorkedService {

	/**
	 * Registra o dia de trabalho.
	 * 
	 * @param worked
	 * @return {@link Worked}
	 */
	public Worked create(Worked worked);
	
	/**
	 * Busca o dia trabalhado, pelo funcinário e data.
	 * 
	 * @param employee
	 * @param momment
	 * @return {@link Worked}
	 */
	public Worked findByEmployeeAndMomment(Employee employee, LocalDate momment);
}
