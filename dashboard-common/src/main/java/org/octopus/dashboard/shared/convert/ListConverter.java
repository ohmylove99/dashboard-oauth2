package org.octopus.dashboard.shared.convert;

import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class ListConverter<S, T> {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T, S> List<T> convert(final List<S> sourceList,
			final Converter<S, T> converter) {
		if (sourceList == null) {
			return null;
		}
		List<T> targets = new ArrayList();
		for (S source : sourceList) {
			targets.add(converter.convert(source));
		}
		return targets;
	}
}
