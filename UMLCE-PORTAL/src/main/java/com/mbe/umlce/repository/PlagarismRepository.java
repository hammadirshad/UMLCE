package com.mbe.umlce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.Mistake;
import com.mbe.umlce.model.Plagarism;
import com.mbe.umlce.model.User;

public interface PlagarismRepository extends CrudRepository<Plagarism,Integer> {
	
	List<Plagarism> findAll();

	Mistake findById(int Id);
	
	List<Plagarism> findByAssignment(Assignment assignment);
	

	List<Plagarism> findByStudent1(User user);

	List<Plagarism> findByStudent1AndAssignment(User user, Assignment assignment);
	List<Plagarism> findByStudent2AndAssignment(User user, Assignment assignment);

}
