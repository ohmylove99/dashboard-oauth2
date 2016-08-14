package org.octopus.dashboard.service;

import java.util.List;

import org.octopus.dashboard.data.entity.Employee;
import org.octopus.dashboard.data.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository repository;

	public void create(Employee employee) {
		repository.save(employee);
	}

	public Page<Employee> getAllPage(Pageable pageable) {
		Page<Employee> employees = repository.findAll(pageable);
		return employees;
	}

	public List<Employee> getAll() {
		List<Employee> employees = (List<Employee>) repository.findAll();
		return employees;
	}
}
