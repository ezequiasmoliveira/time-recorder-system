package com.timerecordersystem.service;

import java.time.LocalDate;
import java.util.List;

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
	 * @param moment
	 * @return {@link Worked}
	 */
	public Worked findByEmployeeAndMoment(Employee employee, LocalDate moment);
	
	/**
	 * Busca os dias trabalhado, do mês e ano informado na data passada por parâmetro.
	 * 
	 * @param employee 
	 * @param moment data
	 * @return uma lista de {@link Worked}, ou array vazio
	 */
	public List<Worked> listDaysWorked(Employee employee, LocalDate moment);
	
	/**
	 * Busca os dias trabalhado, pelo funcionario informado.
	 * 
	 * @param employee
	 * @param moment
	 * @return uma lista de {@link Worked}, ou array vazio
	 */
	public List<Worked> listByEmployeeAndMoment(Employee employee, LocalDate moment);
}
