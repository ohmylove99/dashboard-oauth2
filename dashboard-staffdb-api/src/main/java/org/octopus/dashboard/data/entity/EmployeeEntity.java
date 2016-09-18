package org.octopus.dashboard.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "ss_employee2")
// This is original table object
public class EmployeeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty
	private String name;

	private Long empType;

	private Long empGrade;

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

	public Long getEmpType() {
		return empType;
	}

	public void setEmpType(Long empType) {
		this.empType = empType;
	}

	public Long getEmpGrade() {
		return empGrade;
	}

	public void setEmpGrade(Long empGrade) {
		this.empGrade = empGrade;
	}
}
