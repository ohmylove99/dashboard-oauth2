package org.octopus.dashboard.shared.web.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
	public static String getBaseUrl(HttpServletRequest request) {
		String baseUrl = request.getScheme() + // "http"
				"://" + // "://"
				request.getServerName() + // "myhost"
				":" + // ":"
				request.getServerPort() + // "80"
				request.getContextPath();
		return baseUrl;
	}
}
