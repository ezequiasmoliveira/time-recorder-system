package com.timerecordersystem.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.timerecordersystem.model.Employee;
import com.timerecordersystem.model.TimeRecorder;
import com.timerecordersystem.model.Worked;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
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
		Assertions.assertThat(timeRecorder.getMoment()).isNotNull();
		Assertions.assertThat(timeRecorder.getWorked()).isNotNull();
	}
}
