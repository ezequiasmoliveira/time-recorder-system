package com.timerecordersystem.controller.rest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
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
import com.timerecordersystem.resource.BreakTimeResource;
import com.timerecordersystem.resource.HoursWorkedResource;
import com.timerecordersystem.resource.TimeRecorderResource;
import com.timerecordersystem.resource.WorkingDayResource;
import com.timerecordersystem.service.EmployeeService;
import com.timerecordersystem.service.TimeRecorderService;
import com.timerecordersystem.service.WorkedService;

import io.swagger.annotations.ApiOperation;

@RestController
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
	public ResponseEntity<?> recorder(@RequestBody TimeRecorderResource resource, 
			@AuthenticationPrincipal UserDetails userDetails){
		
		final Employee employee = this.employeeService.findByPis(resource.getPis().toString());
		
		this.timeRecorderService.recorder(employee, new TimeRecorder(resource.getMomment()));
		
		// TODO - implementar mensagem de resposta
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/employees/{pis}/records")
	@ApiOperation(tags = { "Time Recorder" }, value = "Lista os registros do ponto.")
	public ResponseEntity<?> recordList(@PathVariable Long pis, 
			@RequestParam(name = "momment", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate momment, 
			@AuthenticationPrincipal UserDetails userDetails) {
		
		final Employee employee = this.employeeService.findByPis(pis.toString());
		final List<Worked> daysWorkeds = this.workedService.listDaysWorked(employee, momment);
		
		List<WorkingDayResource> resources = new ArrayList<>();
		for (Worked worked : daysWorkeds) {
			WorkingDayResource resource = new WorkingDayResource(worked);
			
			final Long breakTime = this.calculationService.breakTime(worked.getRecords());
			resource.setBreakTime(breakTime);
			
			resources.add(resource);
		}
	
		return new ResponseEntity<>(resources, HttpStatus.OK);
	}
	
	@GetMapping("/employees/{pis}/worked-hours")
	@ApiOperation(tags = { "Time Recorder" }, value = "Horas trabalhadas.")
	public ResponseEntity<?> workedHours(@PathVariable Long pis, 
			@RequestParam(name = "momment") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate momment, 
			@AuthenticationPrincipal UserDetails userDetails) {
		
		final Employee employee = this.employeeService.findByPis(pis.toString());
		final Worked workedDay = this.workedService.findByEmployeeAndMomment(employee, momment);
		final List<Worked> workedMonth = new ArrayList<>();
		
		
		final HoursWorkedResource resource = new HoursWorkedResource();
		resource.setHoursWorkedDay(this.calculationService.calculateTimeWorked(workedDay.getRecords()));
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/employees/{pis}/break-times")
	@ApiOperation(tags = { "Time Recorder" }, value = "Informa se falta algum intervalo de descanso.")
	public ResponseEntity<?> breakTime(@PathVariable Long pis, 
			@RequestParam(name = "momment") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate momment, 
			@AuthenticationPrincipal UserDetails userDetails) {
		
		final Employee employee = this.employeeService.findByPis(pis.toString());
		final Worked worked = this.workedService.findByEmployeeAndMomment(employee, momment);
		
		if (worked == null) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		final BreakTimeResource resource = new BreakTimeResource();
		resource.setMomment(worked.getMomment());
		resource.setWorkedHours(this.calculationWorkedTime(worked.getRecords()));
		resource.setBreakHours(this.calculationBreakTime(worked.getRecords()));
		
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}
	
	/**
	 * Calcula o tempo de descanso.
	 * 
	 * @param records
	 * @return {@link LocalTime}
	 */
	private LocalTime calculationBreakTime(final List<TimeRecorder> records) {
		final Integer breakMinutes = this.calculationService.breakTime(records).intValue();
		final LocalTime breakTimes = LocalTime.of((breakMinutes / 60), (breakMinutes % 60));
		
		return breakTimes;
	}
	
	/**
	 * Calcula o tempo trabalhado.
	 * 
	 * @param records
	 * @return {@link LocalTime}
	 */
	private LocalTime calculationWorkedTime(final List<TimeRecorder> records) {
		final Integer workedMinutes = this.calculationService.calculateTimeWorked(records).intValue();
		final LocalTime workedTimes = LocalTime.of((workedMinutes / 60), (workedMinutes % 60));
		
		return workedTimes;
	}
	
}
