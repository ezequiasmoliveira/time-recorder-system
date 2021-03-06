package com.timerecordersystem.repository;

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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeDAOTest {
	
	@Autowired
	private EmployeeDAO employeeDAO;
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void whenFindByPis_thenReturnEmployee() {
		final Employee employee = new Employee("funcionário 1", "12345678910", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		this.employeeDAO.save(employee);
		
		final Employee employeeTest = this.employeeDAO.findByPis(employee.getPis());
		
		Assertions.assertThat(employeeTest.getId()).isEqualTo(employee.getId());
		Assertions.assertThat(employeeTest.getName()).isEqualTo(employee.getName());
		Assertions.assertThat(employeeTest.getPis()).isEqualTo(employee.getPis());
		Assertions.assertThat(employeeTest.getPassword()).isEqualTo(employee.getPassword());
	}
}
