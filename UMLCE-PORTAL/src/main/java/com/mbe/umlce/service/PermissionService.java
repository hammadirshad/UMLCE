package com.mbe.umlce.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mbe.umlce.model.Permission;
import com.mbe.umlce.repository.PermissionRepository;

@Service
@Transactional
public class PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;

	@PersistenceContext
	private EntityManager em;
	private static final Logger logger = Logger
			.getLogger(PermissionService.class);

	public Permission save(Permission permission) {

		try {
			em.persist(permission);
			logger.info("persist");
		} catch (Exception e) {
			logger.error("Persist Exception", e);
			permission = em.merge(permission);
			logger.info("Merge");
		}
		return permission;
	}

	public List<Permission> findAll() {
		return permissionRepository.findAll();
	}

	public Permission findByName(String name) {
		return permissionRepository.findByName(name);
	}

	public Permission findById(int id) {
		return permissionRepository.findById(id);
	}
}
