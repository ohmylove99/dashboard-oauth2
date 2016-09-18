package org.octopus.dashboard.api.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.octopus.dashboard.Constants;
import org.octopus.dashboard.service.UserMailService;
import org.octopus.dashboard.shared.data.entity.User;
import org.octopus.dashboard.shared.data.rest.UserRepository;
import org.octopus.dashboard.shared.security.AuthoritiesConstants;
import org.octopus.dashboard.shared.web.rest.util.HeaderUtil;
import org.octopus.dashboard.shared.web.rest.util.PaginationUtil;
import org.octopus.dashboard.shared.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestEndPoint {
	private final Logger log = LoggerFactory.getLogger(UserRestEndPoint.class);
	@Inject
	private UserRepository userRepository;

	@Inject
	private UserMailService mailService;

	@RequestMapping(value = "/userinfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal User user)
			throws URISyntaxException {
		User appUser = userRepository.findByLoginName(user.getLoginName());
		return new ResponseEntity<User>(appUser, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public ResponseEntity<Page<User>> getAllUsers(Pageable pageable)
			throws URISyntaxException {
		Page<User> page = userRepository.findAll(pageable);

		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,
				"/api/users");
		return new ResponseEntity<>(page, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Secured(AuthoritiesConstants.ADMIN)
	public ResponseEntity<?> createUser(@RequestBody User user,
			HttpServletRequest request) throws URISyntaxException {
		log.debug("REST request to save User : {}", user);

		// Lowercase the user login before comparing with database
		if (userRepository.findByLoginName(user.getLoginName().toLowerCase()) == null) {
			return ResponseEntity.badRequest()
					.headers(HeaderUtil.createFailureAlert("userManagement", "userexists",
							"Login already in use"))
					.body(null);
		}
		else {
			User newUser = userRepository.save(user);
			String baseUrl = RequestUtil.getBaseUrl(request);
			// mailService(newUser, baseUrl);
			return ResponseEntity.created(new URI("/api/users/" + newUser.getLoginName()))
					.headers(HeaderUtil.createAlert("userManagement.created",
							newUser.getLoginName()))
					.body(newUser);
		}
	}

	@RequestMapping(value = "/users/{login:" + Constants.LOGIN_REGEX
			+ "}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)

	@Secured(AuthoritiesConstants.ADMIN)
	public ResponseEntity<Void> deleteUser(@PathVariable String login) {
		log.debug("REST request to delete User: {}", login);
		User user = userRepository.findByLoginName(login.toLowerCase());
		if (user == null) {
			return ResponseEntity.badRequest().headers(HeaderUtil
					.createFailureAlert("userManagement", "userexists", "User not exsit"))
					.body(null);
		}
		userRepository.delete(user.getId());
		return ResponseEntity.ok()
				.headers(HeaderUtil.createAlert("userManagement.deleted", login)).build();
	}
}
