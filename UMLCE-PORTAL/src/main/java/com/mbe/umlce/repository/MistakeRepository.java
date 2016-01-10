package com.mbe.umlce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.AssignmentSubmission;
import com.mbe.umlce.model.Mistake;
import com.mbe.umlce.model.User;

public interface MistakeRepository extends CrudRepository<Mistake,Integer> {
	
	List<Mistake> findAll();

	Mistake findById(int Id);
	
	List<Mistake> findByMistaker(User mistaker);
	
	List<Mistake> findByAssignment(Assignment assignment);
	
	List<Mistake> findByAssignmentSub(AssignmentSubmission assignmentSubmission);

}
