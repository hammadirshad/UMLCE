package com.mbe.umlce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mbe.umlce.model.Role;
public interface RoleRepository extends CrudRepository<Role, Integer> {

	List<Role> findAll();
	Role findById(int Id);	
}
