package org.octopus.dashboard.shared.data.repository;

import org.octopus.dashboard.shared.data.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	User findByLoginName(String loginName);
}
