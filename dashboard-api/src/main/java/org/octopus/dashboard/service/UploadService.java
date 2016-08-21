package org.octopus.dashboard.service;

import org.octopus.dashboard.data.entity.UploadEntity;
import org.octopus.dashboard.data.entity.UploadMappingEntity;
import org.octopus.dashboard.data.repository.UploadMappingRepository;
import org.octopus.dashboard.data.repository.UploadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UploadService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(UploadService.class);

	@Autowired
	private UploadRepository uploadDao;

	@Autowired
	private UploadMappingRepository uploadMappingDao;

	@Transactional(readOnly = true)
	public UploadEntity get(Long id) {
		return uploadDao.findOne(id);
	}

	@Transactional
	public UploadEntity save(UploadEntity upload) {
		return uploadDao.save(upload);
	}

	@Transactional
	public UploadMappingEntity save(UploadEntity upload,
			UploadMappingEntity uploadMapping) {
		UploadEntity upd = uploadDao.save(upload);
		uploadMapping.setUpload(upd);
		UploadMappingEntity rtn = uploadMappingDao.save(uploadMapping);
		return rtn;
	}

	@Transactional
	public UploadMappingEntity save(UploadMappingEntity uploadMapping) {
		return uploadMappingDao.save(uploadMapping);
	}

	@Transactional
	public void save(UploadEntity[] uploads) {
		if (uploads != null)
			for (UploadEntity upload : uploads) {
				uploadDao.save(upload);
			}
	}

}
