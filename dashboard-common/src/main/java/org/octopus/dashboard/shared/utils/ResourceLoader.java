package org.octopus.dashboard.shared.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;

public class ResourceLoader {

	final static Logger logger = LoggerFactory.getLogger(ResourceLoader.class);

	public static InputStream loadResource(final String name) {

		final String errorMessage = "Unable to load resource [" + name + "]";

		try {
			logger.info("Loading resource [" + name + "]");
			InputStream is = ResourceLoader.class.getResourceAsStream(name);
			if (is == null) {
				throw new RuntimeException(errorMessage);
			}
			return is;
		}
		catch (Exception e) {
			logger.error(errorMessage);
			throw new RuntimeException(errorMessage);
		}
	}

	public static URL getResource(final String name) {

		final String errorMessage = "Unable to load resource [" + name + "]";

		try {
			logger.info("Loading resource [" + name + "]");
			URL url = ResourceLoader.class.getResource(name);
			if (url == null) {
				throw new RuntimeException(errorMessage);
			}
			return url;
		}
		catch (Exception e) {
			logger.error(errorMessage);
			throw new RuntimeException(errorMessage);
		}
	}

	public static String loadResourceAsString(final String name) {
		InputStream is = loadResource(name);
		return convertStreamToString(is);
	}

	public static String loadResourceAsString(final String filename,
			final Map<String, String> replaceTokens) {

		String inputData = loadResourceAsString(filename);

		for (Map.Entry<String, String> entry : replaceTokens.entrySet()) {
			inputData = inputData.replace("${" + entry.getKey() + "}", entry.getValue());
		}

		return inputData;
	}

	@SuppressWarnings("resource")
	private static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

}
