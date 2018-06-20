package com.timerecordersystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.timerecordersystem.erro.BusinessException;
import com.timerecordersystem.model.Employee;
import com.timerecordersystem.model.TimeRecorder;
import com.timerecordersystem.model.Worked;
import com.timerecordersystem.repository.TimeRecorderDAO;
import com.timerecordersystem.service.TimeRecorderService;
import com.timerecordersystem.service.WorkedService;

@Service
@Transactional(propagation = Propagation.MANDATORY, noRollbackFor = Exception.class)
public class TimeRecorderServiceImpl implements TimeRecorderService{
	
	@Autowired
	private TimeRecorderDAO timeRecorderDAO;
	@Autowired
	private WorkedService workedService;

	@Override
	public void recorder(final Employee employee, final TimeRecorder timeRecorder) throws BusinessException {
		Worked worked = this.workedService.findByEmployeeAndMoment(employee, timeRecorder.getMoment().toLocalDate());
		
		if (worked == null) {
			worked = new Worked(employee, timeRecorder.getMoment().toLocalDate());
			// registra o dia de trabalho
			this.workedService.create(worked);
		}
		
		// valida patida do ponto
		if (this.isExistsRecorded(worked, timeRecorder.getMoment())) {
			throw new BusinessException("Ponto já registrado.");
		}
		timeRecorder.setWorked(worked);
		// registra o batida do ponto
		this.timeRecorderDAO.save(timeRecorder);
	}
	
	@Override
	public List<TimeRecorder> findByWorked(final Worked worked) {
		return this.timeRecorderDAO.findByWorked(worked);
	}

	/**
	 * Valida se o ponto já foi batido no minuto atual.<br>
	 * Obs. só é permitido uma batida por minuto.
	 * 
	 * @param worked
	 * @param momment
	 * @return {@link Boolean}
	 */
	private Boolean isExistsRecorded(final Worked worked, final LocalDateTime momment) {
		final LocalDateTime firstMommet = LocalDateTime.of(momment.getYear(), momment.getMonth(), momment.getDayOfMonth(), momment.getHour(), momment.getMinute(), 00, 000);
		final LocalDateTime lastMomment = LocalDateTime.of(momment.getYear(), momment.getMonth(), momment.getDayOfMonth(), momment.getHour(), momment.getMinute(), 59, 999);
		
		final TimeRecorder timeRecorder = this.timeRecorderDAO.findByMomentBetweenAndWorked(firstMommet, lastMomment, worked);
		
		return timeRecorder != null;
	}

}
