package org.octopus.dashboard.shared.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Service
@SuppressWarnings("unchecked")
public class JaxbXmlGenerator {

	/** Used to log messages. */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("rawtypes")
	private Map<Class, JAXBContext> contextCache = new HashMap();

	/**
	 * @param entity
	 */
	@SuppressWarnings("rawtypes")
	public String toXml(final Object entity, final Class... clazz) {

		JAXBContext jc = null;
		try {
			java.io.StringWriter sw = new StringWriter();
			jc = JAXBContext.newInstance(clazz);
			Marshaller marshaller = jc.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			marshaller.marshal(entity, sw);
			return sw.toString();

		}
		catch (JAXBException e) {
			logger.error("Error writing XML", e);
			throw new RuntimeException("Error writing XML", e);
		}
	}

	/**
	 * @param entity
	 */
	public String toXml(final Object entity, final String packages) {

		JAXBContext jc = null;
		try {
			java.io.StringWriter sw = new StringWriter();
			jc = JAXBContext.newInstance(packages);
			Marshaller marshaller = jc.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			marshaller.marshal(entity, sw);
			return sw.toString();

		}
		catch (JAXBException e) {
			logger.error("Error writing XML", e);
			throw new RuntimeException("Error writing XML", e);
		}
	}

	/**
	 * @param entity
	 */
	@SuppressWarnings("rawtypes")
	public String toXml(final Object entity, final Class clazz) {

		JAXBContext jc = null;
		try {
			java.io.StringWriter sw = new StringWriter();
			jc = getContext(clazz);
			Marshaller marshaller = jc.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(entity, sw);
			return sw.toString();

		}
		catch (JAXBException e) {
			logger.error("Error writing XML", e);
			throw new RuntimeException("Error writing XML", e);
		}
	}

	/**
	 * @param xml
	 */
	@SuppressWarnings("rawtypes")
	public Object fromXml(final String xml, final Class... clazz) {

		try {
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Unmarshaller marshaller = jc.createUnmarshaller();

			StringReader reader = new StringReader(xml);
			return marshaller.unmarshal(reader);

		}
		catch (JAXBException e) {
			logger.error("Error reading XML " + xml, e);
			throw new RuntimeException("Error reading XML " + xml, e);
		}
	}

	@SuppressWarnings("rawtypes")
	private JAXBContext getContext(Class clazz) throws JAXBException {
		JAXBContext context = contextCache.get(clazz);
		if (context == null) {
			context = JAXBContext.newInstance(clazz);
			contextCache.put(clazz, context);
		}
		return context;
	}

}
