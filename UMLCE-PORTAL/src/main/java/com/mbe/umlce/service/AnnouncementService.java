package com.mbe.umlce.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mbe.umlce.model.Announcement;
import com.mbe.umlce.repository.AnnouncementRepository;

@Service
@Transactional
public class AnnouncementService {
	@Autowired
	private AnnouncementRepository announcementRepository;
	@PersistenceContext
	private EntityManager em;
	private static final Logger logger = Logger
			.getLogger(SubmissionResourceService.class);

	@Transactional(readOnly = true)
	public List<Announcement> findAll() {
		return announcementRepository.findAll();
	}

	public Announcement save(Announcement announcement) {

		announcement = em.merge(announcement);
		logger.info("Merge");
		return announcement;
	}

	public Announcement findById(int Id) {
		return announcementRepository.findById(Id);
	}

}
