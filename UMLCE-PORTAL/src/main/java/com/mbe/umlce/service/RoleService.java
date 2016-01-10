package com.mbe.umlce.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mbe.umlce.model.Role;
import com.mbe.umlce.repository.RoleRepository;

@Service
@Transactional
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	@PersistenceContext
	private EntityManager em;
	private static final Logger logger = Logger.getLogger(RoleService.class);

	@Transactional(readOnly = true)
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	public Role save(Role role) {

/*		try {
			em.persist(role);
			logger.info("persist");
		} catch (Exception e) {
			logger.error("Persist Exception", e);*/
			role = em.merge(role);
			logger.info("Merge");
/*		}*/
		return role;
	}

	public Role findById(int Id) {
		return roleRepository.findById(Id);
	}
}
