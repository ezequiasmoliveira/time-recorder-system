package com.timerecordersystem.service.impl;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

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
	public Worked findByEmployeeAndMoment(final Employee employee, final LocalDate moment) {
		return this.workedDAO.findByEmployeeAndMoment(employee, moment);
	}

	@Override
	public List<Worked> listDaysWorked(final Employee employee, final LocalDate moment) {
		final LocalDate lastDayOfTheMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
		
		final LocalDate firstMomet = LocalDate.of(moment.getYear(), moment.getMonth(), 01);
		final LocalDate lastMoment = LocalDate.of(moment.getYear(), moment.getMonth(), lastDayOfTheMonth.getDayOfMonth());
		
		return this.workedDAO.findByMomentBetweenAndEmployee(firstMomet, lastMoment, employee);
	}

	@Override
	public List<Worked> listByEmployeeAndMoment(final Employee employee, final LocalDate moment) {
		List<Worked> workeds = new ArrayList<>();
		
		if (moment == null) {
			workeds = this.workedDAO.findByEmployee(employee);
		}else {
			workeds.add(this.workedDAO.findByEmployeeAndMoment(employee, moment));
		}
		
		return workeds;
	}

}
