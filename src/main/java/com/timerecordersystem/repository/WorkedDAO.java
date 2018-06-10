package com.timerecordersystem.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.timerecordersystem.model.Employee;
import com.timerecordersystem.model.Worked;

@RepositoryRestResource
public interface WorkedDAO extends CrudRepository<Worked, Long>{

	/**
	 * Busca o dia trabalhado do funcionário pela data.
	 * 
	 * @param employee 
	 * @param momment 
	 * @return {@link Worked}
	 */
	public Worked findByEmployeeAndMomment(Employee employee, LocalDate momment); 
	
	/**
	 * Busca os dias trabalhado, pelo funcionário informado.
	 * 
	 * @param employee
	 * @return uma lista de {@link Worked}, ou array vazio
	 */
	public List<Worked> findByEmployee(Employee employee);
	
	/**
	 * Busca os dias trabalhado, pelo funcionário informado e intervalo de data.
	 * 
	 * @param firstMomment
	 * @param lastMomment
	 * @param employee
	 * @return uma lista de {@link Worked}, ou array vazio
	 */
	public List<Worked> findByMommentBetweenAndEmployee(LocalDate firstMomment, LocalDate lastMomment, Employee employee);
	
}
