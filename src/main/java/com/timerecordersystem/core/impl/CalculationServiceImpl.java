package com.timerecordersystem.core.impl;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.timerecordersystem.core.CalculationService;
import com.timerecordersystem.model.TimeRecorder;
import com.timerecordersystem.model.Worked;

@Component
public class CalculationServiceImpl implements CalculationService {

	@Override
	public Long breakTime(final List<TimeRecorder> records) {
		final Long minutes = this.calculateTimeWorked(records);
		
		return this.calculateBreak(minutes / 60);
	}

	@Override
	public BigDecimal workedTime(final LinkedList<Worked> records) {
		BigDecimal time = new BigDecimal(0D);  
		
		for (Worked worked : records) {
			switch (worked.getMoment().getDayOfWeek()) {
			case SATURDAY:
				final Long saturday = this.calculateTimeWorked(worked.getRecords(), Boolean.FALSE);
				
				time = time.add(BigDecimal.valueOf(saturday).multiply(BigDecimal.valueOf(1.5))); 
				break;
			case SUNDAY:
				final Long sunday = this.calculateTimeWorked(worked.getRecords(), Boolean.FALSE);
				
				time = time.add(BigDecimal.valueOf(sunday * 2));
				break; 
			default:
				final Long otherDays = this.calculateTimeWorked(worked.getRecords(), Boolean.TRUE);
				
				time = time.add(BigDecimal.valueOf(otherDays));
			}
		}
        	
		return time;
	}
	
	@Override
	public Long calculateTimeWorked(final List<TimeRecorder> records) {
		
		LocalDateTime referenceDate = null;
		Long workTime = 0L;
		for (TimeRecorder record : records) {
			
			if (referenceDate == null) {
				referenceDate = record.getMoment();
			} else {
				final Duration time = Duration.between(referenceDate, record.getMoment());
				
				workTime = workTime + time.toMinutes();
				referenceDate = null;
			}
		}
		
		return workTime;
	}
	
	/**
	 * Calcula o tempo trabalhado. <br>
	 * Se foi realizado trabalho entre as 22:00 e 6:00 será calculado adicional noturno. 
	 * 
	 * @param records batidas registrada 
	 * @param calculateNightWork indica se deve calcular adicional noturno
	 * @return tempo trabalhado, em milissegundos {@link Long}
	 */
	private Long calculateTimeWorked(final List<TimeRecorder> records, final Boolean calculateNightWork) {
		LocalDateTime referenceDate = null;
		Long workTime = 0L;
		Long timeNightWork = 0L;
		
		for (TimeRecorder record : records) {
			
			LocalDateTime tempRecord = null;
			if (calculateNightWork && record.getMoment().getHour() > 21 ) {
				final LocalDateTime newRecord = LocalDateTime.of(
						record.getMoment().getYear(), record.getMoment().getMonth(), record.getMoment().getDayOfMonth(), 22, 00, 00);
				
				final Long nightWork = this.calculateNightWork(newRecord, record.getMoment());
				
				timeNightWork = (timeNightWork + nightWork);
				tempRecord = newRecord.minusSeconds(1);
			}else if (calculateNightWork && record.getMoment().getHour() < 07) {
				final LocalDateTime newRecord = LocalDateTime.of(
						record.getMoment().getYear(), record.getMoment().getMonth(), record.getMoment().getDayOfMonth(), 06, 59, 59);
				
				final Long nightWork = this.calculateNightWork(record.getMoment(), newRecord);
				
				timeNightWork = (timeNightWork + nightWork);
				tempRecord = newRecord.plusSeconds(1);
			} else {
				tempRecord = record.getMoment();
			}
			
		
			if (referenceDate == null) {
				referenceDate = tempRecord;
			} else {
				final Duration time = Duration.between(referenceDate, tempRecord);
				
				workTime = workTime + time.toMillis();
				referenceDate = null;
			}
			
		}
		workTime = (workTime + timeNightWork);
		
		return workTime;
	}
	
	/**
	 * Calcula o tempo trabalhado após as 22:00hs e antes das 7:00hs, adicionando um 
	 * quinto ao tempo trabalhado (t + (t / 5)). 
	 * 
	 * @param firstTime primeiro horário
	 * @param secondTime segundo horário
	 * @return tempo trabalhado, em milissegundos {@link Long}
	 */
	private Long calculateNightWork(final LocalDateTime firstTime, final LocalDateTime secondTime) {
		
		final Duration workTime = Duration.between(firstTime, secondTime);
		final Long nightWork = (workTime.toMillis() + (workTime.toMillis() / 5));
		
		return nightWork;
	}
	
	/**
	 * Calcula o tempo de descanço.
	 * 
	 * @param hours horas trabalhada
	 * @return o tempo, em minutos, de descanço {@link Long}
	 */
	private Long calculateBreak(final Long hours) {
		Long timeBreak = 0L;
		
		if (hours < 4) {
			timeBreak = 0L;
		} else if(hours > 4 && hours < 6){
			timeBreak = 15L;
		} else if (hours > 6) {
			timeBreak = 60L;
		}
		
		return timeBreak;
	}
	
	
}
