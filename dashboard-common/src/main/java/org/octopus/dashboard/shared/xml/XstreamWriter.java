package org.octopus.dashboard.shared.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class XstreamWriter {
	private XStream xstream;

	public XstreamWriter() {
		xstream = new XStream() {
			@Override
			protected MapperWrapper wrapMapper(MapperWrapper next) {
				return new CustomerClassAliasingMapper(next);
			}
		};
	}

	public String toXml(final Object obj) {
		return xstream.toXML(obj);
	}
}
