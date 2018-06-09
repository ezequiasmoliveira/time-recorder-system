package com.timerecordersystem.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.timerecordersystem.model.Employee;
import com.timerecordersystem.model.TimeRecorder;
import com.timerecordersystem.model.Worked;
import com.timerecordersystem.repository.TimeRecorderDAO;
import com.timerecordersystem.service.TimeRecorderService;
import com.timerecordersystem.service.WorkedService;

@Service
@Transactional(propagation = Propagation.MANDATORY, noRollbackFor = Exception.class)
public class TimeRecorderServiceImpl implements TimeRecorderService{
	
	@Autowired
	private TimeRecorderDAO timeRecorderDAO;
	@Autowired
	private WorkedService workedService;

	@Override
	public void recorder(final Employee employee, final TimeRecorder timeRecorder) {
		Worked worked = this.workedService.findByEmployeeAndMomment(employee, timeRecorder.getMomment().toLocalDate());
		
		if (worked == null) {
			worked = new Worked(employee, timeRecorder.getMomment().toLocalDate());
			// registra o dia de trabalho
			this.workedService.create(worked);
		}
		
		// valida patida do ponto
		if (this.isExistsRecorded(worked, timeRecorder.getMomment())) {
			// TODO - mensagem informando que o ponto já foi batido
		}
		
		timeRecorder.setWorked(worked);
		
		// registra o batida do ponto
		this.timeRecorderDAO.save(timeRecorder);
	}

	/**
	 * Valida se o ponto já foi batido no minuto atual.<br>
	 * Obs. só é permitido uma batida por minuto.
	 * 
	 * @param worked
	 * @param momment
	 * @return {@link Boolean}
	 */
	private Boolean isExistsRecorded(final Worked worked, final LocalDateTime momment) {
		// TODO - corrigir consulta
		final TimeRecorder timeRecorder = this.timeRecorderDAO.findByWorkedAndMomment(worked, momment);
		
		return (timeRecorder != null ? Boolean.TRUE : Boolean.FALSE);
	}
	
}
