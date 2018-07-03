package com.timerecordersystem.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.timerecordersystem.model.TimeRecorder;
import com.timerecordersystem.model.Worked;
import com.timerecordersystem.repository.TimeRecorderDAO;
import com.timerecordersystem.service.TimeRecorderService;
import com.timerecordersystem.service.WorkedService;

@RunWith (SpringRunner.class) 
public class TimeRecorderServiceImplTest {

	@TestConfiguration
    static class TimeRecorderServiceImplTestContextConfiguration {
  
        @Bean
        public TimeRecorderService timeRecorderService() {
            return new TimeRecorderServiceImpl();
        }
    }
	
	@Autowired
    private TimeRecorderService timeRecorderService;
	@MockBean
	private TimeRecorderDAO timeRecorderDAO;
	@MockBean
	private WorkedService workedService;
	
	
	@Before
    public void setUp() {
		final Employee john = new Employee("john", "12345678910", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		final Worked worked = new Worked(john, LocalDate.now());

		final LocalDateTime localDateTime = LocalDateTime.of(2018, 01, 15, 13, 01);
		
		final TimeRecorder timeRecorder1 = new TimeRecorder(worked, localDateTime);
		final TimeRecorder timeRecorder2 = new TimeRecorder(worked, localDateTime.plusMinutes(30));
		final TimeRecorder timeRecorder3 = new TimeRecorder(worked, localDateTime.plusMinutes(45));
		final TimeRecorder timeRecorder4 = new TimeRecorder(worked, localDateTime.plusMinutes(90));
		
		final List<TimeRecorder> timeRecordes = Arrays.asList(timeRecorder1, timeRecorder2, timeRecorder3, timeRecorder4);
		
		Mockito.when(timeRecorderDAO.findByWorked(worked)).thenReturn(timeRecordes);
	}
	
	@Test 
	public void whenfindByWorked_thenReturnTimeRecorder() {
		final Employee john = new Employee("john", "12345678910", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		final Worked worked = new Worked(john, LocalDate.now());
		
		final LocalDateTime localDateTime = LocalDateTime.of(2018, 01, 15, 13, 01);
		
		final TimeRecorder timeRecorder1 = new TimeRecorder(worked, localDateTime);
		final TimeRecorder timeRecorder2 = new TimeRecorder(worked, localDateTime.plusMinutes(30));
		final TimeRecorder timeRecorder3 = new TimeRecorder(worked, localDateTime.plusMinutes(45));
		final TimeRecorder timeRecorder4 = new TimeRecorder(worked, localDateTime.plusMinutes(90));

		final List<TimeRecorder> timeRecordes = this.timeRecorderService.findByWorked(worked);
		
		Mockito.verify(timeRecorderDAO, VerificationModeFactory.times(1)).findByWorked(worked);
        Mockito.reset(timeRecorderDAO);
        
		assertThat(timeRecordes).hasSize(4)
				.extracting(TimeRecorder::getMoment)
				.contains(timeRecorder1.getMoment(), 
						timeRecorder2.getMoment(), 
						timeRecorder3.getMoment(), 
						timeRecorder4.getMoment());
	}
	
}
