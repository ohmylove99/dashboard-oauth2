package org.octopus.dashboard.data.repository;

import org.octopus.dashboard.data.entity.EmployeeEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeEntityRepository
		extends PagingAndSortingRepository<EmployeeEntity, Long> {
	EmployeeEntity findByName(String name);
}