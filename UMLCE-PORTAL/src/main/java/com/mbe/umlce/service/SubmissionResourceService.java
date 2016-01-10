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
import com.mbe.umlce.model.SubmissionResource;
import com.mbe.umlce.repository.SubmissionResourceRepository;

@Service
@Transactional
public class SubmissionResourceService {
	
	@Autowired
	private SubmissionResourceRepository submissionResourceRepository;
	@PersistenceContext
	private EntityManager em;
	private static final Logger logger = Logger.getLogger(AssignmentResourceService.class);
	
	@Transactional(readOnly = true)
	public List<SubmissionResource> findAll() {
		return submissionResourceRepository.findAll();
	}

	public SubmissionResource save(SubmissionResource submissionResource) {

		submissionResource = em.merge(submissionResource);
			logger.info("Merge");
		return submissionResource;
	}

	public SubmissionResource findById(int Id) {
		return submissionResourceRepository.findById(Id);
	}
	
	public List<SubmissionResource> findByAssignmentSubmission(AssignmentSubmission assignmentSub) {
		return submissionResourceRepository.findByAssignmentSub(assignmentSub);
	}
	
	public List<SubmissionResource> findByAssignment(Assignment assignment) {
		return submissionResourceRepository.findByAssignment(assignment);
	}


}
