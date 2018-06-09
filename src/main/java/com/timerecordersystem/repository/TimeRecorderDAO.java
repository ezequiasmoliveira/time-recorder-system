package com.timerecordersystem.repository;

import java.time.LocalDateTime;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.timerecordersystem.model.TimeRecorder;
import com.timerecordersystem.model.Worked;

@RepositoryRestResource
public interface TimeRecorderDAO extends CrudRepository<TimeRecorder, Long> {

	public TimeRecorder findByWorkedAndMomment(Worked worked, LocalDateTime momment);
}
