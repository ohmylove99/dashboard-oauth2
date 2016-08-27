package org.octopus.dashboard.data.jdbc;

import java.util.List;

import org.octopus.dashboard.data.jdbc.entity.EmployeeJdbcEntity;
import org.octopus.dashboard.data.jdbc.mapper.EmployeeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EmployeeDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(readOnly = true)
	public List<EmployeeJdbcEntity> findAll() {
		return jdbcTemplate.query(
				"select e.id as id, e.name as name, e.emp_type as empTypeId,t1.name as empType, e.emp_grade as empGradeId, t2.name as empGrade from ss_employee e "
						+ "left join ss_type t1 " //
						+ "on e.emp_type = t1.id  " //
						+ "left join ss_type t2  " + "on e.emp_grade = t2.id",
				new EmployeeRowMapper());
	}
}
