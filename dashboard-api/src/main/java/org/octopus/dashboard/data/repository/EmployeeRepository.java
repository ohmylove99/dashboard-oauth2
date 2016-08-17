package org.octopus.dashboard.data.repository;

import org.octopus.dashboard.data.entity.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
	Employee findByName(String name);
}