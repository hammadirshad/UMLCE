package com.mbe.umlce.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.AssignmentResource;

public interface AssignmentResourceRepository extends CrudRepository<AssignmentResource,Integer> {
	
    ArrayList<AssignmentResource> findAll();

	AssignmentResource findById(int Id);
	
	ArrayList<AssignmentResource> findByAssignment(Assignment assignment);
  
}
