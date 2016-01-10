package com.mbe.umlce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.User;

public interface AssignmentRepository extends CrudRepository<Assignment,Integer> {
	
	List<Assignment> findAll();

	Assignment findById(int Id);
	
	List<Assignment> findByOwner(User owner);
	
	@Modifying(clearAutomatically = true)
	@Transactional
    @Query("Update Assignment a SET a.totalMarks=:TOTALMARKS WHERE a.id=:id")
    public void updateTotalMarks(@Param("id") int id, @Param("TOTALMARKS") double marks);


}
