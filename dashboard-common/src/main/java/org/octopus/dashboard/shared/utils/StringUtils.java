package org.octopus.dashboard.shared.utils;

public class StringUtils {

	public static String toCamelCase(final String init) {
		return init.substring(0, 1).toLowerCase() + init.substring(1);
	}
}
