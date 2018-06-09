package com.timerecordersystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.timerecordersystem.model.TimeRecorder;

@RepositoryRestResource
public interface TimeRecorderDAO extends CrudRepository<TimeRecorder, Long> {

}
