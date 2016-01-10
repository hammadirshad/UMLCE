package com.mbe.umlce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.AssignmentSubmission;
import com.mbe.umlce.model.SubmissionResource;

public interface SubmissionResourceRepository extends CrudRepository<SubmissionResource,Integer> {
	
	List<SubmissionResource> findAll();

	SubmissionResource findById(int Id);
	
	List<SubmissionResource> findByAssignmentSub(AssignmentSubmission assignmentSub);
	
	List<SubmissionResource> findByAssignment(Assignment assignment);

}
