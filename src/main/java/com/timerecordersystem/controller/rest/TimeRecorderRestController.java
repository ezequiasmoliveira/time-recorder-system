package com.timerecordersystem.controller.rest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.timerecordersystem.core.CalculationService;
import com.timerecordersystem.model.Employee;
import com.timerecordersystem.model.TimeRecorder;
import com.timerecordersystem.model.Worked;
import com.timerecordersystem.resource.TimeRecorderResource;
import com.timerecordersystem.resource.WorkedResource;
import com.timerecordersystem.service.EmployeeService;
import com.timerecordersystem.service.TimeRecorderService;
import com.timerecordersystem.service.WorkedService;

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
	@Autowired
	private WorkedService workedService;
	@Autowired
	private CalculationService calculationService;
	
	/**
	 * Registra batida do ponto.
	 * 
	 * @param resource
	 * @return
	 */
	@PostMapping("/records")
	@ApiOperation(tags = { "Time Recorder" }, value = "Registra o ponto.")
	public ResponseEntity<?> recorder(@RequestBody TimeRecorderResource resource){
		final Employee employee = this.employeeService.findByPis(resource.getPis().toString());
		
		this.timeRecorderService.recorder(employee, new TimeRecorder(resource.getMomment()));
		
		// TODO - implementar mensagem de resposta
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/employees/{pis}/records")
	@ApiOperation(tags = { "Time Recorder" }, value = "Lista os registros do ponto.")
	public ResponseEntity<?> recordList(@PathVariable Long pis, 
			@RequestParam(name = "momment", required = false) LocalDate momment) {
		final Employee employee = this.employeeService.findByPis(pis.toString());
		
		final List<Worked> daysWorkeds = this.workedService.listDaysWorked(employee, momment);
		
		List<WorkedResource> resources = new ArrayList<>();
		for (Worked worked : daysWorkeds) {
			WorkedResource resource = new WorkedResource(worked);
			
			final Long breakTime = this.calculationService.breakTime(worked.getRecords());
			resource.setBreakTime(breakTime);
			
			resources.add(resource);
		}
	
		return new ResponseEntity<>(resources, HttpStatus.OK);
	}
	
}
