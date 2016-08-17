package org.octopus.dashboard.shared.data.rest;

import org.octopus.dashboard.shared.data.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "type", path = "api/rest/role")
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
}
