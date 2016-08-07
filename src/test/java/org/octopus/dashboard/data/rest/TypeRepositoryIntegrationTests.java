package org.octopus.dashboard.data.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.octopus.dashboard.data.entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TypeRepositoryIntegrationTests {

	@Autowired
	TypeRepository repository;

	@Test
	public void findsFirstPageOfTypes() {
		Page<Type> types = this.repository.findAll(new PageRequest(0, 10));
		assertThat(types.getTotalElements()).isGreaterThan(9L);
	}

	@Test
	public void findByGrp() {
		Page<Type> types = this.repository.findByGrpIgnoringCase("emptype", new PageRequest(0, 10));
		assertThat(types).isNotNull();
		assertThat(types.getTotalElements()).isGreaterThan(1L);
	}

}
