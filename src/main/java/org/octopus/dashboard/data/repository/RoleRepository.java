package org.octopus.dashboard.data.repository;

import org.octopus.dashboard.data.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
}