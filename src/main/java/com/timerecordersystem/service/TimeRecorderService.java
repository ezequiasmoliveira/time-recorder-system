package com.timerecordersystem.service;

import java.util.List;

import com.timerecordersystem.erro.BusinessException;
import com.timerecordersystem.model.Employee;
import com.timerecordersystem.model.TimeRecorder;
import com.timerecordersystem.model.Worked;

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
	 * @throws BusinessException
	 */
	public void recorder(Employee employee, TimeRecorder timeRecorder) throws BusinessException;
	
	/**
	 * Busca as batidas do ponto pelo dia trabalhado.
	 * 
	 * @param worked
	 * @return uma lista de {@link TimeRecorder}, ou array vazio 
	 */
	public List<TimeRecorder> findByWorked(Worked worked);

}
