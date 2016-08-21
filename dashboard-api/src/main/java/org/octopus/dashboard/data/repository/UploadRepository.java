package org.octopus.dashboard.data.repository;

import org.octopus.dashboard.data.entity.UploadEntity;
import org.springframework.data.repository.CrudRepository;

public interface UploadRepository extends CrudRepository<UploadEntity, Long> {
	UploadEntity findByFileName(String fileName);
}
