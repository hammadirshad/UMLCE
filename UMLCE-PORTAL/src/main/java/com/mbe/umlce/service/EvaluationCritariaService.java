package com.mbe.umlce.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.EvaluationCritaria;
import com.mbe.umlce.repository.EvaluationCritariaRepository;

@Service
@Transactional
public class EvaluationCritariaService {
	
	@Autowired
	private EvaluationCritariaRepository evaluationCritariaRepository;
	@PersistenceContext
	private EntityManager em;
	private static final Logger logger = Logger.getLogger(AssignmentService.class);
	
	@Transactional(readOnly = true)
	public List<EvaluationCritaria> findAll() {
		return evaluationCritariaRepository.findAll();
	}

	public EvaluationCritaria save(EvaluationCritaria evaluationCritaria) {

		evaluationCritaria = em.merge(evaluationCritaria);
			logger.info("Merge");
		return evaluationCritaria;
	}

	public EvaluationCritaria findById(int Id) {
		return evaluationCritariaRepository.findById(Id);
	}
	
	public List<EvaluationCritaria> findByAssignment(Assignment assignment) {
		return evaluationCritariaRepository.findByAssignment(assignment);
	}
	
}
