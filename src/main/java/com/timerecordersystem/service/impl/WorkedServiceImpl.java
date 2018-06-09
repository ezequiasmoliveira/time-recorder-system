package com.timerecordersystem.service.impl;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timerecordersystem.model.Employee;
import com.timerecordersystem.model.Worked;
import com.timerecordersystem.repository.WorkedDAO;
import com.timerecordersystem.service.WorkedService;

@Service
public class WorkedServiceImpl implements WorkedService {
	
	@Autowired
	private WorkedDAO workedDAO; 

	@Override
	public Worked create(final Worked worked) {
		return this.workedDAO.save(worked);
	}

	@Override
	public Worked findByEmployeeAndMomment(final Employee employee, final Date momment) {
		return this.workedDAO.findByEmployeeAndMomment(employee, momment);
	}

}
