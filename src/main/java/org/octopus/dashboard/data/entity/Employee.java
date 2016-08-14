package org.octopus.dashboard.data.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="EMP_TYPE")
	private Type empType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="EMP_GRADE")
	private Type empGrade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getEmpType() {
		return empType;
	}

	public void setEmpType(Type empType) {
		this.empType = empType;
	}

	public Type getEmpGrade() {
		return empGrade;
	}

	public void setEmpGrade(Type empGrade) {
		this.empGrade = empGrade;
	}
}
