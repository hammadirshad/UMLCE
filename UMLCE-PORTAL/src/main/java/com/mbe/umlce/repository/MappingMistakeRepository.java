package com.mbe.umlce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.AssignmentSubmission;
import com.mbe.umlce.model.MappingMistake;
import com.mbe.umlce.model.User;

public interface MappingMistakeRepository extends CrudRepository<MappingMistake,Integer> {
	
	List<MappingMistake> findAll();

	MappingMistake findById(int Id);
	
	List<MappingMistake> findByMistaker(User mistaker);
	
	List<MappingMistake> findByAssignment(Assignment assignment);
	
	List<MappingMistake> findByAssignmentSub(AssignmentSubmission assignmentSubmission);

}
