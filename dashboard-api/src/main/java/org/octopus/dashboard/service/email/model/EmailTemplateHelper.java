package org.octopus.dashboard.service.email.model;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;

import org.octopus.dashboard.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class EmailTemplateHelper {

	private static Logger logger = LoggerFactory.getLogger(EmailTemplateHelper.class);

	@SuppressWarnings("rawtypes")
	public static String generateContent(Template template, Map map)
			throws MessagingException {
		try {
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
		}
		catch (IOException e) {
			logger.error(e.getMessage());
			throw new MessagingException(e.getMessage());
		}
		catch (TemplateException e) {
			logger.error(e.getMessage());
			throw new MessagingException(e.getMessage());
		}
	}

	public static Template getTemplate(Configuration configuration, String tempaltePath)
			throws IOException {
		try {
			return configuration.getTemplate(tempaltePath, Constants.DEFAULT_ENCODING);
		}
		catch (TemplateNotFoundException e) {
			logger.error(e.getMessage());
			throw new IOException(e.getMessage());
		}
		catch (MalformedTemplateNameException e) {
			logger.error(e.getMessage());
			throw new IOException(e.getMessage());
		}
		catch (ParseException e) {
			logger.error(e.getMessage());
			throw new IOException(e.getMessage());
		}
		catch (IOException e) {
			logger.error(e.getMessage());
			throw new IOException(e.getMessage());
		}
	}
}
