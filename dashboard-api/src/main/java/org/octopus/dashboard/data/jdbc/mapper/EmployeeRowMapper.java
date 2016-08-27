package org.octopus.dashboard.data.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.octopus.dashboard.data.jdbc.entity.EmployeeJdbcEntity;
import org.springframework.jdbc.core.RowMapper;

public class EmployeeRowMapper implements RowMapper<EmployeeJdbcEntity> {

	@Override
	public EmployeeJdbcEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		EmployeeJdbcEntity emp = new EmployeeJdbcEntity();
		emp.setId(rs.getLong("id"));
		emp.setName(rs.getString("name"));
		emp.setEmpTypeId(rs.getLong("empTypeId"));
		emp.setEmpType(rs.getString("empType"));
		emp.setEmpGradeId(rs.getLong("empGradeId"));
		emp.setEmpGrade(rs.getString("empGrade"));

		return emp;
	}

}
