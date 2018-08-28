package com.timerecordersystem.repository;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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
	public void whenCreateWithoutEmployee_thenShouldThrowConstraintViolationException() {
		thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("Employee field is required");
        
        final Worked worked = new Worked();
        worked.setMoment(LocalDate.now());
        
		this.workedDAO.save(worked);
	}
	
	@Test
	public void whenCreateWithoutMoment_thenShouldThrowConstraintViolationException() {
		thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("Moment field is required");
        
        final Employee employee = new Employee("funcionário 2", "12345678911", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
        this.employeeDAO.save(employee);
        
        final Worked worked = new Worked();
        worked.setEmployee(employee);
        
		this.workedDAO.save(worked);
	}
	
	@Test
	public void whenFindByEmployeeAndMomment_thenWorkedShouldBeFound() {
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
	public void giverSetOfWorkeds_whenFindByEmployee_thenReturnAllWorkedOfEmployee() {
		final Employee employee = new Employee("funcionário 4", "12345678913", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		this.employeeDAO.save(employee);
		
		final Worked worked1 = new Worked(employee, LocalDate.now());
		final Worked worked2 = new Worked(employee, LocalDate.now().plusDays(2));
		final Worked worked3 = new Worked(employee, LocalDate.now().plusDays(29));
		final Worked worked4 = new Worked(employee, LocalDate.now().plusMonths(1));
		final Worked worked5 = new Worked(employee, LocalDate.now().plusMonths(1).plusDays(5));
		final Worked worked6 = new Worked(employee, LocalDate.now().plusMonths(2));
		final Worked worked7 = new Worked(employee, LocalDate.now().plusMonths(3).plusDays(27));
		
		final List<Worked> workeds = Arrays.asList(worked1, worked2, worked3, worked4, worked5, worked6, worked7);
		this.workedDAO.saveAll(workeds);
		
		final List<Worked> workedsTest = this.workedDAO.findByEmployee(employee);
		
		Assertions.assertThat(workedsTest.size()).isEqualTo(7);
	}
	
	@Test
	public void giverSetOfWorkeds_whenFindByMomentBetweenAndEmployee_thenReturnAllWorkedOfEmployeeAndBetweenMoment() {
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
		
		final List<Worked> allWorkeds = Arrays.asList(worked1, worked2, worked3, worked4, worked5, worked6, worked7);
		this.workedDAO.saveAll(allWorkeds);
		
		final LocalDate lastDayOfTheMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
		final LocalDate firstMommet = LocalDate.of(LocalDate.now().getYear(), localDate.getMonth(), 01);
		final LocalDate lastMomment = LocalDate.of(LocalDate.now().getYear(), localDate.getMonth(), lastDayOfTheMonth.getDayOfMonth());
		
		final List<Worked> workedsTest = this.workedDAO.findByMomentBetweenAndEmployee(firstMommet, lastMomment, employee);
		
		Assertions.assertThat(workedsTest.size()).isEqualTo(7);
	}
	
}
