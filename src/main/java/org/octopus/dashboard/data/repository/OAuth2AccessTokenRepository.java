package org.octopus.dashboard.data.repository;

import org.octopus.dashboard.data.MessageDataBase;
import org.octopus.dashboard.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = "messages")
public class OAuth2AccessTokenRepository {
	private static final Logger logger = LoggerFactory.getLogger(OAuth2AccessTokenRepository.class);
	@Autowired
	private MessageDataBase database;

	@Cacheable
	public Message findByCode(String code) {
		logger.info("---> Loading message with code '" + code + "'");
		return database.getMessage().get(code);
	}
}
