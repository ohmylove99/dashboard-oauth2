package org.octopus.dashboard.shared.convert;

import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

public class SetConverter<S, T> {
	public static <T, S> Set<T> convert(final Set<S> sourceList,
			final Converter<S, T> converter) {
		if (sourceList == null) {
			return null;
		}
		Set<T> targets = new HashSet<>();
		for (S source : sourceList) {
			targets.add(converter.convert(source));
		}
		return targets;
	}
}
