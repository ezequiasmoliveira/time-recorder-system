package com.timerecordersystem;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.timerecordersystem.model.Employee;
import com.timerecordersystem.model.TimeRecorder;
import com.timerecordersystem.model.Worked;
import com.timerecordersystem.repository.EmployeeDAO;
import com.timerecordersystem.repository.TimeRecorderDAO;
import com.timerecordersystem.repository.WorkedDAO;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TimeRecorderDAOTest {

	@Autowired
	private TimeRecorderDAO timeRecorderDAO; 
	@Autowired
	private WorkedDAO workedDAO;
	@Autowired
	private EmployeeDAO employeeDAO;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void createShouldPersisteData() {
		final Employee employee = new Employee("funcionário 1", "12345678910", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		this.employeeDAO.save(employee);
		
		final Worked worked = new Worked(employee, LocalDate.now());
		this.workedDAO.save(worked);
		
		final TimeRecorder timeRecorder = new TimeRecorder(worked, LocalDateTime.now()); 
		this.timeRecorderDAO.save(timeRecorder);
		
		Assertions.assertThat(timeRecorder.getId()).isNotNull();
		Assertions.assertThat(timeRecorder.getMomment()).isNotNull();
		Assertions.assertThat(timeRecorder.getWorked()).isNotNull();
	}
}
