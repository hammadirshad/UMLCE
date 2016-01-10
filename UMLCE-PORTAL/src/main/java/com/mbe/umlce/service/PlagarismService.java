package com.mbe.umlce.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.Mistake;
import com.mbe.umlce.model.Plagarism;
import com.mbe.umlce.model.User;
import com.mbe.umlce.repository.PlagarismRepository;

@Service
@Transactional
public class PlagarismService {
	
	@Autowired
	private PlagarismRepository plagarismRepository;
	@PersistenceContext
	private EntityManager em;
	private static final Logger logger = Logger.getLogger(PlagarismService.class);
	
	@Transactional(readOnly = true)
	public List<Plagarism> findAll() {
		return plagarismRepository.findAll();
	}

	public Plagarism save(Plagarism plagarism) {

		plagarism = em.merge(plagarism);
			logger.info("Merge");
		return plagarism;
	}

	public Mistake findById(int Id) {
		return plagarismRepository.findById(Id);
	}
	
	
	public List<Plagarism> findByStudent1(User user) {
		return plagarismRepository.findByStudent1(user);
	}

	public List<Plagarism> findByStudent1AndAssignment(User user,
			Assignment assignment) {
		return plagarismRepository.findByStudent1AndAssignment(user,assignment);
	}
	
	public List<Plagarism> findByStudent2AndAssignment(User user,
			Assignment assignment) {
		return plagarismRepository.findByStudent2AndAssignment(user,assignment);
	}

}
