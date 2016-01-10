package com.mbe.umlce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mbe.umlce.model.AssignmentSubmission;
import com.mbe.umlce.model.EvaluationError;

public interface EvaluationErrorRepository extends CrudRepository<EvaluationError,Integer> {
	
	List<EvaluationError> findAll();

	EvaluationError findById(int Id);
	
	List<EvaluationError> findBySubmission(AssignmentSubmission assignmentSubmission);

}
