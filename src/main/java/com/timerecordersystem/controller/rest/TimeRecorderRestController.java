package com.timerecordersystem.controller.rest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.timerecordersystem.erro.BusinessException;
import com.timerecordersystem.erro.HttpException;
import com.timerecordersystem.model.Employee;
import com.timerecordersystem.model.TimeRecorder;
import com.timerecordersystem.model.Worked;
import com.timerecordersystem.resource.BreakTimeResource;
import com.timerecordersystem.resource.HoursWorkedResource;
import com.timerecordersystem.resource.RecordResource;
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
	 * @param pis
	 * @param resource
	 * @return
	 * @throws BusinessException
	 */
	@PostMapping("/employees/{pis}/records")
	@ApiOperation(tags = { "Time Recorder" }, value = "Registra o ponto.")
	public ResponseEntity<?> recorder(@PathVariable Long pis, @RequestBody TimeRecorderResource resource) throws Exception {
		
		final Employee employee = this.employeeService.findByPis(resource.getPis().toString());
		if (employee == null) {
			throw new HttpException("Employee not found for PIS: "+ resource.getPis(), HttpStatus.NOT_FOUND);
		}
		this.timeRecorderService.recorder(employee, new TimeRecorder(resource.getMoment()));
		
		final Worked dayWorked = this.workedService.findByEmployeeAndMoment(employee, resource.getMoment().toLocalDate());
		final List<TimeRecorder> records = this.timeRecorderService.findByWorked(dayWorked);
		
		List<TimeRecorderResource> resources = new ArrayList<>();
		records.forEach(timeRecorder -> {
			TimeRecorderResource recorderResource = new TimeRecorderResource();
			recorderResource.setMoment(timeRecorder.getMoment());
			recorderResource.setPis(pis);
			
			resources.add(recorderResource);
		});
		
		return new ResponseEntity<>(resources, HttpStatus.CREATED);
	}
	
	@GetMapping("/employees/{pis}/records")
	@ApiOperation(tags = { "Time Recorder" }, value = "Lista os registros do ponto.")
	public ResponseEntity<?> recordList(@PathVariable Long pis, 
			@RequestParam(name = "moment", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate moment) throws Exception {
		
		final Employee employee = this.employeeService.findByPis(pis.toString());
		if (employee == null) {
			throw new HttpException("Employee not found for PIS: "+ pis, HttpStatus.NOT_FOUND);
		}
		final List<Worked> daysWorkeds = this.workedService.listByEmployeeAndMoment(employee, moment);
		
		if (daysWorkeds == null || daysWorkeds.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<WorkingDayResource> resources = new ArrayList<>();
		daysWorkeds.stream()
			.filter(work -> work != null)
			.forEach(dayWork -> {
					final WorkingDayResource resource = this.builderWorkingDayResource(dayWork);
			
					final Long breakTime = this.calculationService.breakTime(dayWork.getRecords());
					resource.setBreakTime(breakTime);
			
					resources.add(resource);
					}
			);
		
		return new ResponseEntity<>(resources, HttpStatus.OK);
	}
	
	@GetMapping("/employees/{pis}/worked-hours")
	@ApiOperation(tags = { "Time Recorder" }, value = "Horas trabalhadas.")
	public ResponseEntity<?> workedHours(@PathVariable Long pis, 
			@RequestParam(name = "moment") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate moment) throws Exception {
		
		final Employee employee = this.employeeService.findByPis(pis.toString());
		if (employee == null) {
			throw new HttpException("Employee not found for PIS: "+ pis, HttpStatus.NOT_FOUND);
		}
		
		final Worked workedDay = this.workedService.findByEmployeeAndMoment(employee, moment);
		if (workedDay != null) {
			final List<Worked> workedMonth = this.workedService.listDaysWorked(employee, moment);
			
			Long hoursWorkedMonth = 0L;
			for (Worked worked : workedMonth) {
				hoursWorkedMonth = hoursWorkedMonth + this.calculationService.calculateTimeWorked(worked.getRecords());
			}
			
			final HoursWorkedResource resource = new HoursWorkedResource();
			resource.setHoursWorkedDay(this.calculationService.calculateTimeWorked(workedDay.getRecords()));
			resource.setHoursWorkedMonth(hoursWorkedMonth);
			
			return new ResponseEntity<>(hoursWorkedMonth, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/employees/{pis}/break-times")
	@ApiOperation(tags = { "Time Recorder" }, value = "Informa se falta algum intervalo de descanso.")
	public ResponseEntity<?> breakTime(@PathVariable Long pis, 
			@RequestParam(name = "moment") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate moment) throws Exception {
		
		final Employee employee = this.employeeService.findByPis(pis.toString());
		if (employee == null) {
			throw new HttpException("Employee not found for PIS: "+ pis, HttpStatus.NOT_FOUND);
		}
		final Worked worked = this.workedService.findByEmployeeAndMoment(employee, moment);
		
		if (worked == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		final BreakTimeResource resource = new BreakTimeResource();
		resource.setMoment(worked.getMoment());
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
	
	/**
	 * Monta o {@link WorkingDayResource}.
	 * 
	 * @param entity {@link Worked}
	 * @return {@link WorkingDayResource}
	 */
	private WorkingDayResource builderWorkingDayResource(final Worked entity) {
		WorkingDayResource resource = new WorkingDayResource();
		resource.setMoment(entity.getMoment());
		
		List<RecordResource> recordsResource = new ArrayList<>();
		entity.getRecords().forEach(record -> {
			RecordResource recordResource = new RecordResource();
			recordResource.setMoment(record.getMoment());
			
			recordsResource.add(recordResource);
		});
		resource.setRecords(recordsResource);
		
		return resource;
	}
	
}
