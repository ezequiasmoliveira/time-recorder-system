package com.timerecordersystem.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.timerecordersystem.model.Employee;
import com.timerecordersystem.model.TimeRecorder;
import com.timerecordersystem.resource.TimeRecorderResource;
import com.timerecordersystem.service.EmployeeService;
import com.timerecordersystem.service.TimeRecorderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="Time Recorder",  description="Operações referente ao resgistro de ponto.")
@Transactional(rollbackFor = Exception.class)
public class TimeRecorderRestController {
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TimeRecorderService timeRecorderService;
	
	/**
	 * Registra batida do ponto.
	 * 
	 * @param resource
	 * @return
	 */
	@PostMapping("/records")
	@ApiOperation(tags = { "Time Recorder" }, value = "Registra o ponto.")
	public ResponseEntity<?> recorder(@RequestBody TimeRecorderResource resource){
		final Employee employee = this.employeeService.findById(resource.getIdEmployee());
		
		this.timeRecorderService.recorder(employee, new TimeRecorder(resource.getMomment()));
		
		// TODO - implementar mensagem de resposta
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
}
