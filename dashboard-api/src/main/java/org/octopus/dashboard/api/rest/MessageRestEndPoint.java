package org.octopus.dashboard.api.rest;

import org.octopus.dashboard.data.repository.MessageRepository;
import org.octopus.dashboard.domain.Message;
import org.octopus.dashboard.shared.exception.ErrorCode;
import org.octopus.dashboard.shared.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageRestEndPoint {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(MessageRestEndPoint.class);
	@Autowired
	private MessageRepository repository;

	@RequestMapping(value = "/api/rest/message", method = RequestMethod.GET)
	public Message get(@RequestParam("code") String code) {
		Message msg = repository.findByCode(code);
		if (msg == null) {
			throw new ServiceException("message not found code[" + code + "]",
					ErrorCode.INTERNAL_SERVER_ERROR);
		}
		return msg;
	}
}
