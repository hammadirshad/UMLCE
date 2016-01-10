package com.mbe.umlce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.EvaluationCritaria;

public interface EvaluationCritariaRepository extends CrudRepository<EvaluationCritaria,Integer> {
	
	List<EvaluationCritaria> findAll();

	EvaluationCritaria findById(int Id);
	
	List<EvaluationCritaria> findByAssignment(Assignment assignment);


}
