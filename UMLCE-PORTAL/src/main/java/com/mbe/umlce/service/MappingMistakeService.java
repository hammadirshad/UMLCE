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
import com.mbe.umlce.model.MappingMistake;
import com.mbe.umlce.model.User;
import com.mbe.umlce.repository.MappingMistakeRepository;

@Service
@Transactional
public class MappingMistakeService {
	
	@Autowired
	private MappingMistakeRepository mappingMistakeRepository;
	@PersistenceContext
	private EntityManager em;
	private static final Logger logger = Logger.getLogger(MistakeService.class);
	
	@Transactional(readOnly = true)
	public List<MappingMistake> findAll() {
		return mappingMistakeRepository.findAll();
	}

	public MappingMistake save(MappingMistake mistake) {

		mistake = em.merge(mistake);
			logger.info("Merge");
		return mistake;
	}

	public MappingMistake findById(int Id) {
		return mappingMistakeRepository.findById(Id);
	}
	
	public List<MappingMistake> findByMistaker(User mistaker) {
		return mappingMistakeRepository.findByMistaker(mistaker);
	}
	
	public List<MappingMistake> findByAssignment(Assignment assignment) {
		return mappingMistakeRepository.findByAssignment(assignment);
	}
	
	public List<MappingMistake> findByAssignmentSub(AssignmentSubmission assignmentSubmission) {
		return mappingMistakeRepository.findByAssignmentSub(assignmentSubmission);
	}


}
