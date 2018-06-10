package com.timerecordersystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.timerecordersystem.model.Employee;

@RepositoryRestResource
public interface EmployeeDAO extends CrudRepository<Employee, Long> {
	
	/**
	 * Busca o funcionário pelo PIS.
	 * 
	 * @param pis
	 * @return {@link Employee}
	 */
	public Employee findByPis(final String pis);
}
