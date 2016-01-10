package com.mbe.umlce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.mbe.umlce.model.Assignment;
import com.mbe.umlce.model.AssignmentSubmission;
import com.mbe.umlce.model.User;

public interface AssignmentSubmissionRepository extends CrudRepository<AssignmentSubmission,Integer> {
	
	
	List<AssignmentSubmission> findAll();

	AssignmentSubmission findById(int Id);
	
	List<AssignmentSubmission> findByAssignment(Assignment assignment);
	
	List<AssignmentSubmission> findByOwner(User owner);
	
	AssignmentSubmission findByAssignmentAndOwner(Assignment assignment,User owner);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("Update AssignmentSubmission a SET a.marks=:MARKS WHERE a.id=:id")
    public void updateMarks(@Param("id") int id, @Param("MARKS") double marks);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("Update AssignmentSubmission a SET a.plagarism=:PLAGARISM WHERE a.id=:id")
    public void updatePlagarism(@Param("id") int id, @Param("PLAGARISM") double plagarism);
	
}
