package com.mbe.umlce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mbe.umlce.model.PermissionLevel;

public interface PermissionLevelRepository extends
		CrudRepository<PermissionLevel, Integer> {

	List<PermissionLevel> findAll();

	PermissionLevel findByName(String name);

	PermissionLevel findById(int id);
}
