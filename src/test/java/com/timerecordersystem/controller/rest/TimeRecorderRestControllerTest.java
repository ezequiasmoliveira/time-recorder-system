package com.timerecordersystem.controller.rest;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.timerecordersystem.model.Employee;
import com.timerecordersystem.model.Worked;
import com.timerecordersystem.repository.EmployeeDAO;
import com.timerecordersystem.repository.WorkedDAO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TimeRecorderRestControllerTest {
	
	@Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;
    @MockBean
    private EmployeeDAO employeeDAO;
    @MockBean
    private WorkedDAO workedDAO;
    @Autowired
    private MockMvc mockMvc;
	
	@TestConfiguration
    static class Config {
        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().basicAuthorization("funcionario", "12345678910");
        }
    }
	
	@Before
    public void setup() {
        Employee employee = new Employee(1L, "funcionário", "12345678910", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
        
        BDDMockito.when(employeeDAO.findByPis(employee.getPis())).thenReturn(employee);
    }
	
	@Test
    public void loginUser() throws Exception {
        this.mockMvc.perform(get("/").with(httpBasic("funcionario", "12345678910")))
                .andExpect(authenticated());
    }

    @Test
    public void loginInvalidUser() throws Exception {
        MvcResult result = this.mockMvc.perform(formLogin().user("invalid").password("invalid"))
                .andExpect(unauthenticated())
                .andExpect(status().is4xxClientError())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("HTTP Status 401"));
    }
	
	@WithMockUser(value = "funcionario", password = "12345678910")
	@Test
    public void listStudentsWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200() {
		Employee employee = new Employee(1L, "funcionário", "12345678910", "$2a$10$a25kI5Gb5uoAocvFXY41duCcuEqZAI6anzeAt4FMsN2khlX4KduxG");
		List<Worked> workeds = Arrays.asList(new Worked(1L, employee, LocalDate.now()), new Worked(2L, employee, LocalDate.now()));
		
        BDDMockito.when(workedDAO.findByEmployee(employee)).thenReturn(workeds);
        
        
        ResponseEntity<?> response = restTemplate.getForEntity("/employees/12345678910/recordssd", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }
	
	

}
