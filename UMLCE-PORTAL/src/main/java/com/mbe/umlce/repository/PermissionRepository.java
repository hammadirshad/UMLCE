package com.mbe.umlce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mbe.umlce.model.Permission;
public interface PermissionRepository extends CrudRepository<Permission, Integer> {

	List<Permission> findAll();
	Permission findByName(String name);	
	Permission findById(int id);
}
