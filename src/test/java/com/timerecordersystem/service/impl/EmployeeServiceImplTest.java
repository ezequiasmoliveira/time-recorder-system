package com.timerecordersystem.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.timerecordersystem.repository.EmployeeDAO;
import com.timerecordersystem.service.EmployeeService;

@RunWith (SpringRunner.class) 
public class EmployeeServiceImplTest {

	@TestConfiguration
    static class Config {
  
        @Bean
        public EmployeeService employeeService() {
            return new EmployeeServiceImpl();
        }
    }
	
	@Autowired
    private EmployeeService employeeService;
	@MockBean
	private EmployeeDAO employeeDAO;
	
	
	@Before
    public void setUp() {
		final Employee john = new Employee("john", "12345678910", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
        final Employee alex = new Employee("alex", "12345678911", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");;

        Mockito.when(employeeDAO.findByPis(john.getPis())).thenReturn(john);
        Mockito.when(employeeDAO.findByPis(alex.getPis())).thenReturn(alex);
        Mockito.when(employeeDAO.findByPis("12345678978")).thenReturn(null);
	}

	@Test
	public void whenFindByPis_thenReturnEmployee() {
		final Employee john = employeeService.findByPis("12345678910");
		assertThat(john.getName()).isEqualTo("john");
		
		Mockito.verify(employeeDAO, VerificationModeFactory.times(1)).findByPis(Mockito.anyString());
        Mockito.reset(employeeDAO);
	}
	
}
