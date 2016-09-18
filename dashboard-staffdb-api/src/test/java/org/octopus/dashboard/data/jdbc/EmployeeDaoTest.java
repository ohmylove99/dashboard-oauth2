package org.octopus.dashboard.data.jdbc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.octopus.dashboard.data.jdbc.entity.EmployeeJdbcEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class EmployeeDaoTest {

	@Autowired
	private EmployeeDao dao;

	@Test
	public void findAllUsers() {
		List<EmployeeJdbcEntity> users = dao.findAll();
		assertNotNull(users);
		assertTrue(!users.isEmpty());
	}
}
