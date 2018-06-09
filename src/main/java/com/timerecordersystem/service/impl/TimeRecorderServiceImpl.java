package com.timerecordersystem.service.impl;

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
		
		timeRecorder.setWorked(worked);
		
		// registra o batida do ponto
		this.timeRecorderDAO.save(timeRecorder);
	}
	
}
