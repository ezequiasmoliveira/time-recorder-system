package com.timerecordersystem.repository;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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
import org.springframework.test.context.junit4.SpringRunner;

import com.timerecordersystem.model.Employee;
import com.timerecordersystem.model.Worked;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WorkedDAOTest {

	@Autowired
	private WorkedDAO workedDAO;
	@Autowired
	private EmployeeDAO employeeDAO;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void createShouldPersisteData() {
		final Employee employee = new Employee("funcionário 2", "12345678911", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		this.employeeDAO.save(employee);
		
		final Worked worked = new Worked(employee, LocalDate.now());
		
		this.workedDAO.save(worked);
		Assertions.assertThat(worked.getId()).isNotNull();
		
		Assertions.assertThat(worked.getMoment()).isNotNull();
		Assertions.assertThat(worked.getEmployee()).isNotNull();
	}
	
	@Test
	public void createShouldThrowConstraintViolationException() {
		thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("Employee field is required");
        
        final Worked worked = new Worked();
        worked.setMoment(LocalDate.now());
        
		this.workedDAO.save(worked);
	}
	
	@Test
	public void findByEmployeeAndMommentShouldReturnData() {
		final Employee employee = new Employee("funcionário 3", "12345678912", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		this.employeeDAO.save(employee);
		
		final Worked worked = new Worked(employee, LocalDate.now());
		
		this.workedDAO.save(worked);
		final Worked workedTest = this.workedDAO.findByEmployeeAndMoment(employee, LocalDate.now());
		
		Assertions.assertThat(workedTest.getId()).isEqualTo(worked.getId());
		Assertions.assertThat(workedTest.getMoment()).isEqualTo(worked.getMoment());
		Assertions.assertThat(workedTest.getEmployee()).isEqualTo(worked.getEmployee());
	}
	
	@Test
	public void findByEmployeeShouldReturnData() {
		final Employee employee = new Employee("funcionário 4", "12345678913", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		this.employeeDAO.save(employee);
		
		final Worked worked1 = new Worked(employee, LocalDate.now());
		final Worked worked2 = new Worked(employee, LocalDate.now().plusDays(2));
		final Worked worked3 = new Worked(employee, LocalDate.now().plusDays(29));
		final Worked worked4 = new Worked(employee, LocalDate.now().plusMonths(1));
		final Worked worked5 = new Worked(employee, LocalDate.now().plusMonths(1).plusDays(5));
		final Worked worked6 = new Worked(employee, LocalDate.now().plusMonths(2));
		final Worked worked7 = new Worked(employee, LocalDate.now().plusMonths(3).plusDays(27));
		
		final List<Worked> workeds = new ArrayList<>();
		workeds.add(worked1);
		workeds.add(worked2);
		workeds.add(worked3);
		workeds.add(worked4);
		workeds.add(worked5);
		workeds.add(worked6);
		workeds.add(worked7);
		
		this.workedDAO.saveAll(workeds);
		final List<Worked> workedsTest = this.workedDAO.findByEmployee(employee);
		
		Assertions.assertThat(workedsTest.size()).isEqualTo(7);
	}
	
	@Test
	public void findByMommentBetweenAndEmployeeShouldReturnData() {
		final Employee employee = new Employee("funcionário 5", "12345678914", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		this.employeeDAO.save(employee);
		
		final LocalDate localDate = LocalDate.of(2018, 1, 1);
		
		final Worked worked1 = new Worked(employee, localDate);
		final Worked worked2 = new Worked(employee, localDate.plusDays(2));
		final Worked worked3 = new Worked(employee, localDate.plusDays(3));
		final Worked worked4 = new Worked(employee, localDate.plusDays(5));
		final Worked worked5 = new Worked(employee, localDate.plusDays(9));
		final Worked worked6 = new Worked(employee, localDate.plusDays(13));
		final Worked worked7 = new Worked(employee, localDate.plusDays(29));
		
		final List<Worked> workeds = new ArrayList<>();
		workeds.add(worked1);
		workeds.add(worked2);
		workeds.add(worked3);
		workeds.add(worked4);
		workeds.add(worked5);
		workeds.add(worked6);
		workeds.add(worked7);
		
		this.workedDAO.saveAll(workeds);
		
		final LocalDate lastDayOfTheMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
		final LocalDate firstMommet = LocalDate.of(LocalDate.now().getYear(), localDate.getMonth(), 01);
		final LocalDate lastMomment = LocalDate.of(LocalDate.now().getYear(), localDate.getMonth(), lastDayOfTheMonth.getDayOfMonth());
		
		final List<Worked> workedsTest = this.workedDAO.findByMomentBetweenAndEmployee(firstMommet, lastMomment, employee);
		
		Assertions.assertThat(workedsTest.size()).isEqualTo(7);
	}
	
}
