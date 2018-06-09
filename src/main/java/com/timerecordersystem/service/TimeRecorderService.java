package com.timerecordersystem.service;

import com.timerecordersystem.model.Employee;
import com.timerecordersystem.model.TimeRecorder;

/**
 * Interface que fornece os serviços para a patida do ponto.
 * 
 * @author ezequias.oliveira
 *
 */
public interface TimeRecorderService {
	
	/**
	 * Registra batida do ponto, para o funcinário informado.
	 * 
	 * @param employee
	 * @param timeRecorder
	 */
	public void recorder(Employee employee, TimeRecorder timeRecorder);

}
