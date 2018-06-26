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
	 * Busca o funcionário pelo pis.
	 * 
	 * @param pis
	 * @return {@link Employee}
	 */
	public Employee findByPis(String pis);

}
