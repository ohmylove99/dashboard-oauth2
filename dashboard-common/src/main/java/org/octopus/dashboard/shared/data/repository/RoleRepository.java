package org.octopus.dashboard.shared.data.repository;

import org.octopus.dashboard.shared.data.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
}
