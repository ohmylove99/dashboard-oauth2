package org.octopus.dashboard.shared.data.rest;

import org.octopus.dashboard.shared.data.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "type", path = "api/rest/type")
public interface TypeRepository extends PagingAndSortingRepository<Type, Long> {
	Page<Type> findByGrpIgnoringCase(@Param("grp") String grp, Pageable pageable);

	Type findByNameIgnoringCase(@Param("name") String name);
}
