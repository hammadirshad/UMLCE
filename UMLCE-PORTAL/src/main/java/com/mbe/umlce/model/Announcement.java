package com.mbe.umlce.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ANNOUNCEMENT")
public class Announcement {

	@Id
	@SequenceGenerator(name = "announcementSeq", sequenceName = "ANNOUNCEMENT_SEQ", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "announcementSeq")
	@Column(name = "ANNOUNCEMENT_ID", unique = true, nullable = false)
	private int id;
	private String announcement;
	private Date date;
//	private List<User> users;
}
