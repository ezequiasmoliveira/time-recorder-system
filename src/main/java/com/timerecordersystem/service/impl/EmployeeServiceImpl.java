package com.timerecordersystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timerecordersystem.model.Employee;
import com.timerecordersystem.repository.EmployeeDAO;
import com.timerecordersystem.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeDAO employeeDAO;

	@Override
	public Employee create(final Employee employee) {
		return this.employeeDAO.save(employee);
	}

	@Override
	public Employee update(final Employee employee) {
		return this.employeeDAO.save(employee);
	}

	@Override
	public Employee findById(final Long id) {
		return this.employeeDAO.findById(id).orElse(null);
	}

	@Override
	public Employee findByPis(final String pis) {
		return this.employeeDAO.findByPis(pis);
	}

}
