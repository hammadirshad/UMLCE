package com.mbe.umlce.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mbe.umlce.model.AssignmentSubmission;
import com.mbe.umlce.model.EvaluationError;
import com.mbe.umlce.repository.EvaluationErrorRepository;

@Service
@Transactional
public class EvaluationErrorService {
	
	@Autowired
	private EvaluationErrorRepository evaluationErrorRepository;
	@PersistenceContext
	private EntityManager em;
	private static final Logger logger = Logger.getLogger(AssignmentService.class);
	
	@Transactional(readOnly = true)
	public List<EvaluationError> findAll() {
		return evaluationErrorRepository.findAll();
	}

	public EvaluationError save(EvaluationError evaluationError) {

		evaluationError = em.merge(evaluationError);
			logger.info("Merge");
		return evaluationError;
	}

	public EvaluationError findById(int Id) {
		return evaluationErrorRepository.findById(Id);
	}
	
	public List<EvaluationError> findBySubmission(AssignmentSubmission submission) {
		return evaluationErrorRepository.findBySubmission(submission);
	}
	
}
