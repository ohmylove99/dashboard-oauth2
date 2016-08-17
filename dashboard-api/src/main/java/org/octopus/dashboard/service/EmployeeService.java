package org.octopus.dashboard.service;

import java.util.List;

import org.octopus.dashboard.data.entity.Employee;
import org.octopus.dashboard.data.entity.EmployeeEntity;
import org.octopus.dashboard.data.repository.EmployeeEntityRepository;
import org.octopus.dashboard.data.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "employees")
public class EmployeeService {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	@Autowired
	private EmployeeRepository repository;

	@Autowired
	private EmployeeEntityRepository employeeEntityRepository;

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

	@Cacheable
	public EmployeeEntity findByName(String name) {
		logger.info("---> Loading employee with name '" + name + "'");
		return employeeEntityRepository.findByName(name);
	}
}
