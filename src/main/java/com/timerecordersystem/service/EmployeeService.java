package com.timerecordersystem.service;

import com.timerecordersystem.model.Employee;

/**
 * Interface que fornece os serviços para manter o funcionário.
 *  
 * @author ezequias.oliveira
 *
 */
public interface EmployeeService {
	
	/**
	 * Cria um novo funcionário.
	 * 
	 * @param employee
	 * @return {@link Employee}
	 */
	public Employee create(Employee employee);
	
	/**
	 * Atualiza as informações do funcionário.
	 * 
	 * @param employee
	 * @return {@link Employee}
	 */
	public Employee update(Employee employee);
	
	/**
	 * Deleta o funcionário.
	 * 
	 * @param employee
	 */
	public void delete(Employee employee);
	
	/**
	 * Busca o funcionário pelo id.
	 * 
	 * @param id
	 * @return {@link Employee}
	 */
	public Employee findById(Long id);

}
