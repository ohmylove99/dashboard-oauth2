package org.octopus.dashboard.shared.xml;

import static org.octopus.dashboard.shared.utils.StringUtils.toCamelCase;

import com.thoughtworks.xstream.mapper.ClassAliasingMapper;
import com.thoughtworks.xstream.mapper.Mapper;

public class CustomerClassAliasingMapper extends ClassAliasingMapper {

	public CustomerClassAliasingMapper(Mapper wrapped) {
		super(wrapped);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class realClass(String elementName) {
		try {
			return null;
		}
		catch (IllegalArgumentException e) {
		}
		return super.realClass(elementName);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String serializedClass(Class type) {
		try {
			return toCamelCase(type.getSimpleName());
		}
		catch (IllegalArgumentException e) {
		}
		return super.serializedClass(type);
	}
}
