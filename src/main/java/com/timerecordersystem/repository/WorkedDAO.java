package com.timerecordersystem.repository;

import java.sql.Date;

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
	public Worked findByEmployeeAndMomment(Employee employee, Date momment); 
}
