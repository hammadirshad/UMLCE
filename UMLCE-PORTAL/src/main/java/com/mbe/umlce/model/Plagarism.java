package com.mbe.umlce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="PLAGARISM")
public class Plagarism {
	@Id
	@SequenceGenerator(name = "plagarismSeq", sequenceName = "PLAGARISM_SEQ", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plagarismSeq")
	@Column(name = "PLAGARISM_ID", unique = true, nullable = false)
	private int id;
	@ManyToOne(targetEntity=User.class)
	private User student1;
	@ManyToOne(targetEntity=User.class)
	private User student2;
	private float plagPersentage;
	@ManyToOne(targetEntity=Assignment.class)
	private Assignment assignment;
	
	
	public Plagarism()
	{
		
	}

	
	
	public Plagarism(User student1, User student2, float plagPersentage,
			Assignment assignment) {
		super();
		this.student1 = student1;
		this.student2 = student2;
		this.plagPersentage = plagPersentage;
		this.assignment = assignment;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getStudent1() {
		return student1;
	}

	public void setStudent1(User student1) {
		this.student1 = student1;
	}

	public User getStudent2() {
		return student2;
	}

	public void setStudent2(User student2) {
		this.student2 = student2;
	}

	public float getPlagPersentage() {
		return plagPersentage;
	}

	public void setPlagPersentage(float plagPersentage) {
		this.plagPersentage = plagPersentage;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}
}
