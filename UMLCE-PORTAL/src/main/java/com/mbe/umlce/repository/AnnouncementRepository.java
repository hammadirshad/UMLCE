package com.mbe.umlce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mbe.umlce.model.Announcement;

public interface AnnouncementRepository extends CrudRepository<Announcement,Integer> {

	List<Announcement> findAll();

	Announcement findById(int Id);
}
