package com.mbe.umlce.service;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.AssignmentResource;
import com.mbe.umlce.repository.AssignmentResourceRepository;

@Service
@Transactional
public class AssignmentResourceService {
	
	@Autowired
	private AssignmentResourceRepository assignmentResourceRepository;
	@PersistenceContext
	private EntityManager em;
	private static final Logger logger = Logger.getLogger(SubmissionResourceService.class);
	
	@Transactional(readOnly = true)
	public ArrayList<AssignmentResource> findAll() {
		return assignmentResourceRepository.findAll();
	}

	public AssignmentResource save(AssignmentResource assignmentResource) {

		assignmentResource = em.merge(assignmentResource);
			logger.info("Merge");
		return assignmentResource;
	}

	public AssignmentResource findById(int Id) {
		return assignmentResourceRepository.findById(Id);
	}
	
	public ArrayList<AssignmentResource> findByAssignment(Assignment assignment) {
		return assignmentResourceRepository.findByAssignment(assignment);
	}


}