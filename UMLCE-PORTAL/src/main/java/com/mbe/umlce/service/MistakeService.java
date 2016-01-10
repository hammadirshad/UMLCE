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
import com.mbe.umlce.model.Mistake;
import com.mbe.umlce.model.User;
import com.mbe.umlce.repository.MistakeRepository;

@Service
@Transactional
public class MistakeService {
	
	@Autowired
	private MistakeRepository mistakeRepository;
	@PersistenceContext
	private EntityManager em;
	private static final Logger logger = Logger.getLogger(MistakeService.class);
	
	@Transactional(readOnly = true)
	public List<Mistake> findAll() {
		return mistakeRepository.findAll();
	}

	public Mistake save(Mistake mistake) {

		mistake = em.merge(mistake);
			logger.info("Merge");
		return mistake;
	}

	public Mistake findById(int Id) {
		return mistakeRepository.findById(Id);
	}
	
	public List<Mistake> findByMistaker(User mistaker) {
		return mistakeRepository.findByMistaker(mistaker);
	}
	
	public List<Mistake> findByAssignment(Assignment assignment) {
		return mistakeRepository.findByAssignment(assignment);
	}
	
	public List<Mistake> findByAssignmentSub(AssignmentSubmission assignmentSubmission) {
		return mistakeRepository.findByAssignmentSub(assignmentSubmission);
	}


}
