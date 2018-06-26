package com.timerecordersystem.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.timerecordersystem.model.TimeRecorder;
import com.timerecordersystem.model.Worked;

@RepositoryRestResource
public interface TimeRecorderDAO extends CrudRepository<TimeRecorder, Long> {

	/**
	 * Busca a batida de ponto pelo dia trabalhado.
	 * 
	 * @param firstMoment data inicio
	 * @param lastMoment data fim
	 * @param worked dia trabalhado
	 * @return uma lista de {@link TimeRecorder}, ou array vazio
	 */
	public  List<TimeRecorder> findByMomentBetweenAndWorked(LocalDateTime firstMoment, LocalDateTime lastMoment, Worked worked);
	
	/**
	 * Busca os registro de batidas pelo dia trabalhado.
	 * 
	 * @param worked dia trabalhado
	 * @return uma lista de {@link TimeRecorder}, ou array vazio
	 */
	public List<TimeRecorder> findByWorked(Worked worked);
}
