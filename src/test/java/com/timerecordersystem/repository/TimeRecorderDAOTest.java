package com.timerecordersystem.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
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
	
	@Test
	public void createDuplicateShouldThrowDataIntegrityViolationException() {
		thrown.expect(DataIntegrityViolationException.class);
		
		final Employee employee = new Employee("funcionário 1", "12345678911", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		this.employeeDAO.save(employee);
		
		final Worked worked = new Worked(employee, LocalDate.now());
		this.workedDAO.save(worked);
		
		final LocalDateTime time = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), LocalDateTime.now().getHour(), LocalDateTime.now().getMinute());
		
		final TimeRecorder timeRecorder = new TimeRecorder(worked, time);
		this.timeRecorderDAO.save(timeRecorder);
		
		final TimeRecorder timeRecorder2 = new TimeRecorder(worked, time);
		this.timeRecorderDAO.save(timeRecorder2);
	}
	
	@Test
	public void createWithoutWorkedANDMomentShouldPersisteData() {
		thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("Worked field is required");
        thrown.expectMessage("Moment field is required");
        
		final TimeRecorder timeRecorder = new TimeRecorder();
		
		this.timeRecorderDAO.save(timeRecorder);
	}
	
	@Test
	public void createWithoutWorkedShouldPersisteData() {
		thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("Worked field is required");
        
		final TimeRecorder timeRecorder = new TimeRecorder();
		timeRecorder.setMoment(LocalDateTime.now());
		
		this.timeRecorderDAO.save(timeRecorder);
	}
	
	@Test
	public void createWithoutMomentShouldPersisteData() {
		thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("Moment field is required");
        
        final Employee employee = new Employee("funcionário 2", "12345678912", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		this.employeeDAO.save(employee);
		
		final Worked worked = new Worked(employee, LocalDate.now());
		this.workedDAO.save(worked);
		
		final TimeRecorder timeRecorder = new TimeRecorder();
		timeRecorder.setWorked(worked);
		
		this.timeRecorderDAO.save(timeRecorder);
	}
	
	@Test
	public void findByWorkedShouldReturnData() {
		final Employee employee = new Employee("funcionário 3", "12345678913", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		this.employeeDAO.save(employee);
		
		final Worked worked = new Worked(employee, LocalDate.now());
		this.workedDAO.save(worked);
		
		final TimeRecorder timeRecorder1 = new TimeRecorder(worked, LocalDateTime.now());
		final TimeRecorder timeRecorder2 = new TimeRecorder(worked, LocalDateTime.now().plusMinutes(5));
		final TimeRecorder timeRecorder3 = new TimeRecorder(worked, LocalDateTime.now().plusMinutes(12));
		
		List<TimeRecorder> timeRecorders = new ArrayList<>();
		timeRecorders.add(timeRecorder1);
		timeRecorders.add(timeRecorder2);
		timeRecorders.add(timeRecorder3);
		
		this.timeRecorderDAO.saveAll(timeRecorders);
		
		final List<TimeRecorder> timeRecorderTest = this.timeRecorderDAO.findByWorked(worked);
		
		Assertions.assertThat(timeRecorderTest.size()).isEqualTo(3);
	}
	
	@Test
	public void findByMomentBetweenAndWorkedShouldReturnData() {
		final Employee employee = new Employee("funcionário 4", "12345678914", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		this.employeeDAO.save(employee);
		
		final Worked worked = new Worked(employee, LocalDate.now());
		this.workedDAO.save(worked);
		
		final TimeRecorder timeRecorder = new TimeRecorder(worked, LocalDateTime.now());
		this.timeRecorderDAO.save(timeRecorder);
		
		final List<TimeRecorder> timeRecorderTest = this.timeRecorderDAO.findByWorked(worked);
		
		Assertions.assertThat(timeRecorderTest.size()).isEqualTo(3);
	}
	
}
