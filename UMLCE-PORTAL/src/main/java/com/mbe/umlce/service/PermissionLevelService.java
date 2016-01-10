package com.mbe.umlce.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mbe.umlce.model.PermissionLevel;
import com.mbe.umlce.repository.PermissionLevelRepository;

@Service
@Transactional
public class PermissionLevelService {

	@Autowired
	private PermissionLevelRepository permissionLevelRepository;

	@PersistenceContext
	private EntityManager em;
	private static final Logger logger = Logger
			.getLogger(PermissionLevelService.class);

	public PermissionLevel save(PermissionLevel permission) {
		try {
			em.persist(permission);
			logger.info("persist");
		} catch (Exception e) {
			logger.error("Merge Exception", e);
			permission = em.merge(permission);
			logger.info("Merge");
		}
		return permission;
	}
	
	public PermissionLevel findByName(String name) {
		return permissionLevelRepository.findByName(name);
	}
}
