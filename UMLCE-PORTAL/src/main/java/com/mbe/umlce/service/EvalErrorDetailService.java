package com.mbe.umlce.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mbe.umlce.model.EvalErrorDetail;
import com.mbe.umlce.model.EvaluationError;
import com.mbe.umlce.repository.EvalErrorDetailRepository;

@Service
@Transactional
public class EvalErrorDetailService {
	
	@Autowired
	private EvalErrorDetailRepository evalErrorDetailRepository;
	@PersistenceContext
	private EntityManager em;
	private static final Logger logger = Logger.getLogger(AssignmentService.class);
	
	@Transactional(readOnly = true)
	public List<EvalErrorDetail> findAll() {
		return evalErrorDetailRepository.findAll();
	}

	public EvalErrorDetail save(EvalErrorDetail evalErrorDetail) {

		evalErrorDetail = em.merge(evalErrorDetail);
			logger.info("Merge");
		return evalErrorDetail;
	}

	public EvalErrorDetail findById(int Id) {
		return evalErrorDetailRepository.findById(Id);
	}
	
	public List<EvalErrorDetail> findByError(EvaluationError error) {
		return evalErrorDetailRepository.findByError(error);
	}
	
}
