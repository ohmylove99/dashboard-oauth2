package org.octopus.dashboard.service;

import org.octopus.dashboard.shared.data.entity.Type;
import org.octopus.dashboard.shared.data.rest.TypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "types")
public class TypeService {
	private static final Logger logger = LoggerFactory.getLogger(TypeService.class);

	@Autowired
	private TypeRepository repository;

	@Cacheable
	public Type findOne(Long id) {
		logger.info("---> Loading employee with id '" + id + "'");
		return repository.findOne(id);
	}
}
