package com.timerecordersystem.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.timerecordersystem.model.Employee;
import com.timerecordersystem.model.Worked;
import com.timerecordersystem.repository.WorkedDAO;
import com.timerecordersystem.service.WorkedService;

@RunWith (SpringRunner.class) 
public class WorkedServiceImplTest {

	@TestConfiguration
    static class WorkedServiceImplTestContextConfiguration {
  
        @Bean
        public WorkedService workedService() {
            return new WorkedServiceImpl();
        }
    }
	
	@Autowired
    private WorkedService workedService;
	@MockBean
	private WorkedDAO workedDAO;
	
	@Before
    public void setUp() {
		final LocalDate localDate = LocalDate.of(2018, 01, 15);
		
		final Employee john = new Employee("john", "12345678910", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		final Worked worked1 = new Worked(john, localDate.minusDays(2));
		final Worked worked2 = new Worked(john, localDate.minusDays(1));
		final Worked worked3 = new Worked(john, localDate);
		
		final List<Worked> woerkeds = Arrays.asList(worked1, worked2, worked3);
		
		final LocalDate lastDayOfTheMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
		
		final LocalDate firstMomet = LocalDate.of(localDate.getYear(), localDate.getMonth(), 01);
		final LocalDate lastMoment = LocalDate.of(localDate.getYear(), localDate.getMonth(), lastDayOfTheMonth.getDayOfMonth());
		
		Mockito.when(workedDAO.findByEmployeeAndMoment(john, localDate)).thenReturn(worked3);
		Mockito.when(workedDAO.findByEmployee(john)).thenReturn(woerkeds);
		Mockito.when(workedDAO.findByMomentBetweenAndEmployee(firstMomet, lastMoment, john)).thenReturn(woerkeds);
	}
	
	@Test 
	public void whenFindByEmployeeAndMoment_thenReturnWorked() {
		final LocalDate localDate = LocalDate.of(2018, 01, 15);
		
		final Employee john = new Employee("john", "12345678910", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		final Worked worked = workedService.findByEmployeeAndMoment(john, localDate);
		assertThat(worked.getEmployee().getName()).isEqualTo("john");
		
		Mockito.verify(workedDAO, VerificationModeFactory.times(1)).findByEmployeeAndMoment(john, localDate);
        Mockito.reset(workedDAO);
	}
	
	@Test 
	public void whenListByEmployeeAndMoment_thenReturnWorked() {
		final LocalDate localDate = LocalDate.of(2018, 01, 15);
		
		final Employee john = new Employee("john", "12345678910", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		final Worked worked1 = new Worked(john, localDate.minusDays(2));
		final Worked worked2 = new Worked(john, localDate.minusDays(1));
		final Worked worked3 = new Worked(john, localDate);
		
		final List<Worked> workeds = workedService.listByEmployeeAndMoment(john, null);
		
		Mockito.verify(workedDAO, VerificationModeFactory.times(1)).findByEmployee(john);
        Mockito.reset(workedDAO);
        
        assertThat(workeds).hasSize(3).extracting(Worked::getMoment).contains(worked1.getMoment(), worked2.getMoment(), worked3.getMoment());
	}
	
	@Test 
	public void whenListDaysWorked_thenReturnWorked() {
		final LocalDate localDate = LocalDate.of(2018, 01, 15);
		
		final Employee john = new Employee("john", "12345678910", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		final Worked worked1 = new Worked(john, localDate.minusDays(2));
		final Worked worked2 = new Worked(john, localDate.minusDays(1));
		final Worked worked3 = new Worked(john, localDate);
		
		final List<Worked> workeds = workedService.listDaysWorked(john, localDate);
		
		final LocalDate lastDayOfTheMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
		
		final LocalDate firstMomet = LocalDate.of(localDate.getYear(), localDate.getMonth(), 01);
		final LocalDate lastMoment = LocalDate.of(localDate.getYear(), localDate.getMonth(), lastDayOfTheMonth.getDayOfMonth());
		
		Mockito.verify(workedDAO, VerificationModeFactory.times(1)).findByMomentBetweenAndEmployee(firstMomet, lastMoment, john);
        Mockito.reset(workedDAO);
        
        assertThat(workeds).hasSize(3).extracting(Worked::getMoment).contains(worked1.getMoment(), worked2.getMoment(), worked3.getMoment());
	}
	
	@Test 
	public void whenMissingDate_listDaysWorked_thenNotReturnWorked() {
		final Employee john = new Employee("john", "12345678910", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		
		final List<Worked> workeds = workedService.listDaysWorked(john, LocalDate.now());
		
		final LocalDate lastDayOfTheMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
		
		final LocalDate firstMomet = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 01);
		final LocalDate lastMoment = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), lastDayOfTheMonth.getDayOfMonth());
		
		Mockito.verify(workedDAO, VerificationModeFactory.times(1)).findByMomentBetweenAndEmployee(firstMomet, lastMoment, john);
        Mockito.reset(workedDAO);
        
        assertThat(workeds).hasSize(0);
	}
}
