package org.octopus.dashboard.data.jdbc.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class EmployeeJdbcEntity {
	private Long id;

	private String name;

	private Long empTypeId;
	private String empType;

	private Long empGradeId;
	private String empGrade;

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

	public Long getEmpTypeId() {
		return empTypeId;
	}

	public void setEmpTypeId(Long empTypeId) {
		this.empTypeId = empTypeId;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public Long getEmpGradeId() {
		return empGradeId;
	}

	public void setEmpGradeId(Long empGradeId) {
		this.empGradeId = empGradeId;
	}

	public String getEmpGrade() {
		return empGrade;
	}

	public void setEmpGrade(String empGrade) {
		this.empGrade = empGrade;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
