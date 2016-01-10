package com.mbe.umlce.service;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mbe.umlce.beans.UpdateUserBean;
import com.mbe.umlce.model.Role;
import com.mbe.umlce.model.User;
import com.mbe.umlce.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@PersistenceContext
	private EntityManager em;
	private static final Logger logger = Logger.getLogger(UserService.class);

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Transactional(readOnly = true)
	public List<User> findAll() {
		return userRepository.findAll();
	}

	// @Secured("ADMIN")
	public User save(User user) {
		user = em.merge(user);
		logger.info("Merge");
		return user;
	}	

	public User update(User user,Set<Role> roles) {
		for (Role r : roles) {
			Role role=em.merge(r);
			user.getRoles().add(role);
			user=em.merge(user);
		}
		return user;
	}
	
	public void updatePassword(String pass,String username)
	{
		userRepository.updatePassword(username, pass);
	}
	
	public void updateProfile(UpdateUserBean user)
	{
		userRepository.updateProfile(user.getUsername(), user.getFname(), user.getLname(),
				user.getEmail(), user.getPhoneno(), user.getAddress());
	}

}
