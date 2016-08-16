package org.octopus.dashboard.api.rest;

import java.net.URI;
import java.util.List;

import org.octopus.dashboard.data.entity.Employee;
import org.octopus.dashboard.service.EmployeeService;
import org.octopus.dashboard.shared.constants.MediaTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class EmployeeRestEndPoint {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(EmployeeRestEndPoint.class);

	@Autowired
	private EmployeeService service;

	private static final String PAGE_SIZE = "3";

	@RequestMapping(value = "/api/rest/employee", method = RequestMethod.GET)
	public List<Employee> getAll() {
		return service.getAll();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/api/rest/employee", method = RequestMethod.POST, consumes = MediaTypes.JSON)
	public ResponseEntity<?> create(@RequestBody Employee employee,
			UriComponentsBuilder uriBuilder) {

		service.create(employee);

		Long id = employee.getId();
		URI uri = uriBuilder.path("/api/employee/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/api/rest/employee/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Page<Employee> getPage(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Page<Employee> employees = service.getAllPage(pageRequest);
		return employees;
	}

	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		}
		else if ("name".equals(sortType)) {
			sort = new Sort(Direction.ASC, "name");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
}