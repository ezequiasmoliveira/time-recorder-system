package com.timerecordersystem.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.timerecordersystem.model.Employee;
import com.timerecordersystem.resource.TimeRecorderResource;
import com.timerecordersystem.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="Time Recorder", description="Operações referente ao resgistro de ponto.")
public class TimeRecorderRestController {
	
	@Autowired
	private EmployeeService employeeService; 
	
	/**
	 * Registra a batida do ponto.
	 * 
	 * @param resource
	 * @return
	 */
	@PostMapping("/records")
	@ApiOperation(tags = { "Time Recorder" }, value = "Registra o ponto.")
	public ResponseEntity<?> recorder(@RequestBody TimeRecorderResource resource){
		final Employee employee = this.employeeService.findById(resource.getIdEmployee());
		
		
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
