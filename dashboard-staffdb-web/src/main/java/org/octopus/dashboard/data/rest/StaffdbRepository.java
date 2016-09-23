package org.octopus.dashboard.data.rest;

import java.util.List;

import org.octopus.dashboard.data.entity.Staffdb;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "api/rest/staffdb")
public interface StaffdbRepository extends PagingAndSortingRepository<Staffdb, Long> {
	Staffdb findByName(String name);

	Staffdb findBySoeid(String soeid);

	Staffdb findByGeid(Long geid);

	List<Staffdb> findByDept(String dept);

	List<Staffdb> findByUnit(String unit);

	List<Staffdb> findByGoc(String goc);
}
