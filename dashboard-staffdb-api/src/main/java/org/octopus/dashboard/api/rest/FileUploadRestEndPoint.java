package org.octopus.dashboard.api.rest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.octopus.dashboard.config.AppProperties;
import org.octopus.dashboard.data.entity.UploadEntity;
import org.octopus.dashboard.data.entity.UploadMappingEntity;
import org.octopus.dashboard.domain.FileMeta;
import org.octopus.dashboard.service.UploadService;
import org.octopus.dashboard.shared.utils.Ids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class FileUploadRestEndPoint {

	private static Logger logger = LoggerFactory.getLogger(FileUploadRestEndPoint.class);

	private LinkedList<FileMeta> files = new LinkedList<FileMeta>();

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private UploadService uploadServcie;

	/**
	 * if success fileMeta:status:ok;else status:fail
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/api/upload", method = RequestMethod.POST)
	public @ResponseBody LinkedList<FileMeta> upload(MultipartHttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "tableName", required = false) String tableName,
			@RequestParam(value = "tableId", required = false) Long tableId,
			@RequestBody UploadMappingEntity uploadMapping) {

		Iterator<String> itr = request.getFileNames();
		while (itr.hasNext()) {
			MultipartFile mpf = request.getFile(itr.next());
			uploadFile(mpf, tableName, tableId);
		}

		return files;
	}

	@RequestMapping(value = "/api/upload/{userId}", method = RequestMethod.POST)
	public @ResponseBody LinkedList<FileMeta> upload(
			@PathVariable("userId") String userId, MultipartHttpServletRequest request,
			HttpServletResponse response) {

		Iterator<String> itr = request.getFileNames();
		while (itr.hasNext()) {
			MultipartFile mpf = request.getFile(itr.next());
			uploadFile(mpf, userId, null, null);
		}

		return files;
	}

	@RequestMapping(value = "/api/upload/{userId}/{tableName}-{tableId}", method = RequestMethod.POST)
	public @ResponseBody LinkedList<FileMeta> upload(
			@PathVariable("userId") String userId,
			@PathVariable("tableName") String tableName,
			@PathVariable("tableId") Long tableId, MultipartHttpServletRequest request,
			HttpServletResponse response) {

		Iterator<String> itr = request.getFileNames();
		while (itr.hasNext()) {
			MultipartFile mpf = request.getFile(itr.next());
			uploadFile(mpf, userId, tableName, tableId);
		}

		return files;
	}

	@RequestMapping(value = "/api/upload/{id}", method = RequestMethod.GET)
	public void get(HttpServletResponse response, @PathVariable Long id) {
		try {
			UploadEntity upload = uploadServcie.get(id);
			response.setContentType(upload.getFileType());
			response.setHeader("Content-disposition",
					"attachment; filename=\"" + upload.getFileName() + "\"");
			FileCopyUtils.copy(upload.getContent(), response.getOutputStream());
		}
		catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	// =============================== Private

	private void uploadFile(MultipartFile mpf, String tableName, Long tableId) {
		uploadFile(mpf, null, tableName, tableId);
	}

	private void uploadFile(MultipartFile mpf, String userId, String tableName,
			Long tableId) {

		logger.info(mpf.getOriginalFilename() + " uploaded! " + files.size());

		limitFilesQueue();
		FileMeta fileMeta = buildFileMeta(mpf);
		try {

			fileMeta.setBytes(mpf.getBytes());

			String fileLocation = saveFileToDisk(mpf);

			UploadEntity upd = saveFileToDB(fileLocation, fileMeta, userId);

			fileMeta.setStatus("ok");

			if (!StringUtils.isEmpty(tableName) && (tableId != null && tableId > 0)) {
				UploadMappingEntity uploadMapping = new UploadMappingEntity();
				uploadMapping.setTableName(tableName);
				uploadMapping.setTableId(tableId);
				uploadMapping.setUpload(upd);
				uploadServcie.save(uploadMapping);
			}

		}
		catch (IOException e) {
			logger.error(e.getMessage());
			fileMeta.setStatus("fail");
		}
		finally {
			files.add(fileMeta);
		}
	}

	private FileMeta buildFileMeta(MultipartFile mpf) {
		FileMeta fileMeta = new FileMeta();
		fileMeta.setFileName(mpf.getOriginalFilename());
		fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
		fileMeta.setFileType(mpf.getContentType());
		return fileMeta;
	}

	private UploadEntity saveFileToDB(String fileLocation, FileMeta fileMeta,
			String userId) {
		UploadEntity upload = fileMeta.buildEntity();
		upload.setFileLocation(fileLocation);
		upload.setUpdateTime(new Date());
		upload.setUpdateBy(userId);
		UploadEntity result = uploadServcie.save(upload);

		result.setFileLink("/api/upload/" + result.getId());
		result.setFileLocation(fileLocation);

		fileMeta.setId(result.getId());
		result.setUpdateTime(new Date());
		result.setUpdateBy(userId);
		return uploadServcie.save(result);
	}

	private String saveFileToDisk(MultipartFile mpf)
			throws IOException, FileNotFoundException {
		String fileLocation = appProperties.getUpload().getUploadPath() + "/"
				+ Ids.uuid2() + "/" + mpf.getOriginalFilename();
		logger.debug("trying to save file to:" + fileLocation);

		File file = new File(fileLocation);
		if (!file.exists()) {
			FileUtils.forceMkdir(file);
		}

		FileCopyUtils.copy(mpf.getBytes(),
				new FileOutputStream(fileLocation + "/" + mpf.getOriginalFilename()));
		logger.debug("file save to:" + fileLocation);
		return fileLocation;
	}

	// if files > 10 remove the first from the list
	private void limitFilesQueue() {
		if (files.size() >= 10)
			files.pop();
	}
}
