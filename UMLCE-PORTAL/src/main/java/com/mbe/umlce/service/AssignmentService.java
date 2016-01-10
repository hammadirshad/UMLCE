package com.mbe.umlce.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.User;
import com.mbe.umlce.repository.AssignmentRepository;

@Service
@Transactional
public class AssignmentService {
	
	@Autowired
	private AssignmentRepository assignmentRepository;
	@PersistenceContext
	private EntityManager em;
	private static final Logger logger = Logger.getLogger(AssignmentService.class);
	
	@Transactional(readOnly = true)
	public List<Assignment> findAll() {
		return assignmentRepository.findAll();
	}

	public Assignment save(Assignment assignment) {

		assignment = em.merge(assignment);
			logger.info("Merge");
		return assignment;
	}

	public Assignment findById(int Id) {
		return assignmentRepository.findById(Id);
	}
	
	public List<Assignment> findByOwner(User owner) {
		return assignmentRepository.findByOwner(owner);
	}
	

	public void updateTotalMarks(int id,double marks)
	{
		assignmentRepository.updateTotalMarks(id,marks);
	}
}
