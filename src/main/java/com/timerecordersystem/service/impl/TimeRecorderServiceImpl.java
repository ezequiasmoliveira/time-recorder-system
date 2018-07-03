package com.timerecordersystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.timerecordersystem.erro.BusinessException;
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
	public void recorder(final Employee employee, final TimeRecorder timeRecorder) throws BusinessException, DataIntegrityViolationException {
		Worked worked = this.workedService.findByEmployeeAndMoment(employee, timeRecorder.getMoment().toLocalDate());
		
		if (worked == null) {
			worked = new Worked(employee, timeRecorder.getMoment().toLocalDate());
			// registra o dia de trabalho
			this.workedService.create(worked);
		}
		
		timeRecorder.setMoment(this.treatDateRecorder(timeRecorder.getMoment()));
		timeRecorder.setWorked(worked);
		
		// registra o batida do ponto
		this.timeRecorderDAO.save(timeRecorder);
	}

	/**
	 * Trata a data de batida do ponto, deixando os segundo com o valor zerado.
	 * 
	 * @param timeRecorder
	 * @return {@link LocalDateTime}
	 */
	private LocalDateTime treatDateRecorder(final LocalDateTime timeRecorder) {
		
		return LocalDateTime.of(timeRecorder.getYear(), timeRecorder.getMonth(), timeRecorder.getDayOfMonth(), timeRecorder.getHour(), timeRecorder.getMinute());
	}
	
	@Override
	public List<TimeRecorder> findByWorked(final Worked worked) {
		return this.timeRecorderDAO.findByWorked(worked);
	}

}
