package org.octopus.dashboard.shared.data.rest;

import org.octopus.dashboard.shared.data.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "type", path = "api/rest/user")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	User findByLoginName(String loginName);
}
