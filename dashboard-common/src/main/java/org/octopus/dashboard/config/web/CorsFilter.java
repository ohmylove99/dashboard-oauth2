package org.octopus.dashboard.config.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter extends OncePerRequestFilter {
	private final Logger logger = LoggerFactory.getLogger(WebConfigurer.class);
	public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		logger.debug("INSIDE CORS FILTER request method " + request.getMethod()
				+ " request url " + request.getRequestURI());

		dumpRequestHeaders(request, request.getHeaderNames());
		dumpResponseHeaders(response, response.getHeaderNames());

		if (!response.containsHeader(ACCESS_CONTROL_ALLOW_ORIGIN)) {
			logger.debug("Adding header for http://localhost:8080 request uri is "
					+ request.getRequestURI() + " origin:" + request.toString());
			response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");// http://localhost:3000");
		}
		else {
			logger.debug("Already contains header "
					+ response.getHeader(ACCESS_CONTROL_ALLOW_ORIGIN));
		}

		/**
		 * Access-Control-Allow-Headers: Origin, X-Atmosphere-tracking-id,
		 * X-Atmosphere-Framework, X-Cache-Date, Content-Type, X-Atmosphere-Transport, *
		 * Access-Control-Allow-Methods: POST, GET, OPTIONS , PUT
		 * Access-Control-Allow-Origin: * Access-Control-Request-Headers: Origin,
		 * X-Atmosphere-tracking-id, X-Atmosphere-Framework, X-Cache-Date, Content-Type,
		 * X-Atmosphere-Transport, * Content-Type: application/json
		 */

		// if ("OPTIONS".equals(request.getMethod())) {
		response.setHeader("Access-Control-Allow-Methods",
				"POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"authorization, content-type, origin, referer, X-Requested-With, accept, accept-encoding, accept-language, Origin, X-Atmosphere-tracking-id, X-Atmosphere-Framework, X-Cache-Date, Content-Type, X-Atmosphere-Transport,  *");
		response.setHeader("Access-Control-Request-Headers",
				"authorization, content-type, origin, referer, X-Requested-With, accept, accept-encoding, accept-language, Origin, X-Atmosphere-tracking-id, X-Atmosphere-Framework, X-Cache-Date, Content-Type, X-Atmosphere-Transport,  *");

		// Not sure this is needed
		response.setHeader("Access-Control-Allow-Credentials", "true");
		// }

		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			logger.debug("Returning ok to options request");
			response.setStatus(HttpServletResponse.SC_OK);
		}
		else {
			logger.debug("calling rest of filter chain");
			filterChain.doFilter(request, response);
		}
	}

	private void dumpRequestHeaders(final HttpServletRequest request,
			final Enumeration<String> headerNames) {

		while (headerNames.hasMoreElements()) {
			String header = headerNames.nextElement();
			logger.debug(header + " " + request.getHeader(header));
		}
	}

	private void dumpResponseHeaders(final HttpServletResponse response,
			final Collection<String> headerNames) {

		for (String header : headerNames) {
			logger.debug(header + " " + response.getHeader(header));
		}
	}
}