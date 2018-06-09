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
		final Worked worked = this.workedService.findByEmployeeAndMomment(employee, this.convertDate(timeRecorder.getMomment()));
		
	}
	
	/**
	 * Converte uma data do tipo java.util.Date no tipo java.sql.Date.
	 *  
	 * @param utilDate data que deseja converter
	 * @return {@link java.sql.Date}
	 */
	private java.sql.Date convertDate(final java.util.Date utilDate){
		return new java.sql.Date(utilDate.getTime());
	}

}
