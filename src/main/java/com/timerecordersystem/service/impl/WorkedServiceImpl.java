package com.timerecordersystem.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
	public Worked findByEmployeeAndMomment(final Employee employee, final LocalDate momment) {
		return this.workedDAO.findByEmployeeAndMomment(employee, momment);
	}

	@Override
	public List<Worked> listDaysWorked(final Employee employee, final LocalDate momment) {
		final LocalDate lastDayOfTheMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
		
		final LocalDate firstMommet = LocalDate.of(momment.getYear(), momment.getMonth(), 01);
		final LocalDate lastMomment = LocalDate.of(momment.getYear(), momment.getMonth(), lastDayOfTheMonth.getDayOfMonth());
		
		return this.workedDAO.findByMommentBetweenAndEmployee(firstMommet, lastMomment, employee);
	}

	@Override
	public List<Worked> listByEmployeeAndMomment(final Employee employee, final LocalDate momment) {
		List<Worked> workeds = new ArrayList<>();
		
		if (momment == null) {
			workeds = this.workedDAO.findByEmployee(employee);
		}else {
			workeds.add(this.workedDAO.findByEmployeeAndMomment(employee, momment));
		}
		
		return workeds;
	}

}
