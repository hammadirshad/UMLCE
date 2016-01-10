package com.mbe.umlce.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.AssignmentSubmission;
import com.mbe.umlce.model.User;
import com.mbe.umlce.repository.AssignmentSubmissionRepository;

@Service
@Transactional
public class AssignmentSubmissionService {
	
	@Autowired
	private AssignmentSubmissionRepository assignmentSubmissionRepository;
	@PersistenceContext
	private EntityManager em;
	private static final Logger logger = Logger.getLogger(AssignmentSubmissionService.class);
	
	@Transactional(readOnly = true)
	public List<AssignmentSubmission> findAll() {
		return assignmentSubmissionRepository.findAll();
	}

	public AssignmentSubmission save(AssignmentSubmission assignmentSubmission) {

		assignmentSubmission = em.merge(assignmentSubmission);
			logger.info("Merge");
		return assignmentSubmission;
	}

	public AssignmentSubmission findById(int Id) {
		return assignmentSubmissionRepository.findById(Id);
	}
	
	public List<AssignmentSubmission> findByAssignment(Assignment assignment) {
		return assignmentSubmissionRepository.findByAssignment(assignment);
	}
	
	public List<AssignmentSubmission> findByOwner(User owner) {
		return assignmentSubmissionRepository.findByOwner(owner);
	}
	
	public AssignmentSubmission findByAssignmentOwner(Assignment assignment,User owner) {
		return assignmentSubmissionRepository.findByAssignmentAndOwner(assignment,owner);
	}

	
	public void updateMarks(int id,double marks)
	{
		assignmentSubmissionRepository.updateMarks(id,marks);
	}
	
	public void updatePlagarism(int id,double plagarism)
	{
		assignmentSubmissionRepository.updatePlagarism(id, plagarism);
	}

}
