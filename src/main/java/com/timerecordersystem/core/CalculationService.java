package com.timerecordersystem.core;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import com.timerecordersystem.model.TimeRecorder;
import com.timerecordersystem.model.Worked;

/**
 * Interface responsáveis pelos serviços de calculo do ponto.
 * 
 * @author ezequias.oliveira
 *
 */
public interface CalculationService {
	
	/**
	 * Calcula o intervalo de descanso.
	 * 
	 * @param records batidas registrada
	 * @return minutos de descanso {@link Long} 
	 */
	public Long breakTime(final List<TimeRecorder> records);
	
	/**
	 * Calcula o tempo trabalhado.
	 * 
	 * @param works dias trabalhados
	 * @return milissegundos trabalhado {@link BigDecimal}
	 */
	public BigDecimal workedTime(final LinkedList<Worked> works);
	
	/**
	 * Calcula tempo trabalhado.
	 * 
	 * @param records batidas registrada
	 * @return tempo trabalhado, em minutos {@link Long}
	 */
	public Long calculateTimeWorked(List<TimeRecorder> records);

}
