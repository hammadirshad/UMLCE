package com.mbe.umlce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.mbe.umlce.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	List<User> findAll();

	User findByUsername(String username);

	// long count();

	// void deleteAll();

	// void delete(User user);

	// Iterable<User> save(Iterable<T extends User> users);

	// boolean exists(User user);
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("Update User a SET a.password=:PASSWORD WHERE a.username=:username")
    public void updatePassword(@Param("username") String username, @Param("PASSWORD") String password);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("Update User a SET a.fname=:FNAME,a.lname=:LNAME,a.email=:EMAIL,a.phone=:PHONE,a.address=:ADDRESS WHERE a.username=:username")
    public void updateProfile(@Param("username") String username, @Param("FNAME") String fname, @Param("LNAME") String lname, @Param("EMAIL") String email, @Param("PHONE") String phone, @Param("ADDRESS") String address);

}
