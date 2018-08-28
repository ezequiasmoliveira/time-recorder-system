package com.timerecordersystem.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
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
	public void whenCreateDuplicateTimeRecorder_thenShouldThrowDataIntegrityViolationException() {
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
	public void whenCreateWithoutWorkedAndMoment_thenShouldThrowConstraintViolationException() {
		thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("Worked field is required");
        thrown.expectMessage("Moment field is required");
        
		final TimeRecorder timeRecorder = new TimeRecorder();
		
		this.timeRecorderDAO.save(timeRecorder);
	}
	
	@Test
	public void whenCreateWithoutWorked_thenShouldThrowConstraintViolationException() {
		thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("Worked field is required");
        
		final TimeRecorder timeRecorder = new TimeRecorder();
		timeRecorder.setMoment(LocalDateTime.now());
		
		this.timeRecorderDAO.save(timeRecorder);
	}
	
	@Test
	public void whenCreateWithoutMoment_thenShouldThrowConstraintViolationException() {
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
	public void whenFindByWorked_thenReturn3TimeRecorder() {
		final Employee employee = new Employee("funcionário 3", "12345678913", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		this.employeeDAO.save(employee);
		
		final Worked worked = new Worked(employee, LocalDate.now());
		this.workedDAO.save(worked);
		
		final TimeRecorder timeRecorder1 = new TimeRecorder(worked, LocalDateTime.now());
		final TimeRecorder timeRecorder2 = new TimeRecorder(worked, LocalDateTime.now().plusMinutes(5));
		final TimeRecorder timeRecorder3 = new TimeRecorder(worked, LocalDateTime.now().plusMinutes(12));
		
		final List<TimeRecorder> timeRecorders = Arrays.asList(timeRecorder1, timeRecorder2, timeRecorder3);
		this.timeRecorderDAO.saveAll(timeRecorders);
		
		final List<TimeRecorder> timeRecorderTest = this.timeRecorderDAO.findByWorked(worked);
		
		Assertions.assertThat(timeRecorderTest.size()).isEqualTo(3);
	}
	
	@Test
	public void whenFindByMomentBetweenAndWorked_thenReturn3TimeRecorder() {
		final Employee employee = new Employee("funcionário 4", "12345678914", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		this.employeeDAO.save(employee);
		
		final Worked worked = new Worked(employee, LocalDate.now());
		this.workedDAO.save(worked);
		
		final TimeRecorder timeRecorder1 = new TimeRecorder(worked, LocalDateTime.now());
		final TimeRecorder timeRecorder2 = new TimeRecorder(worked, LocalDateTime.now().plusMinutes(5));
		final TimeRecorder timeRecorder3 = new TimeRecorder(worked, LocalDateTime.now().plusMinutes(12));
		
		final List<TimeRecorder> timeRecorders = Arrays.asList(timeRecorder1, timeRecorder2, timeRecorder3);
		this.timeRecorderDAO.saveAll(timeRecorders);
		
		final LocalDateTime firstMomet = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 0, 0);
		final LocalDateTime lastMoment = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 23, 59);
		final List<TimeRecorder> timeRecorderTest = this.timeRecorderDAO.findByMomentBetweenAndWorked(firstMomet, lastMoment, worked);
		
		Assertions.assertThat(timeRecorderTest.size()).isEqualTo(3);
	}
	
}
