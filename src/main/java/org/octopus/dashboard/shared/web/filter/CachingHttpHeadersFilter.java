package org.octopus.dashboard.shared.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CachingHttpHeadersFilter implements Filter {

	private final static long LAST_MODIFIED = System.currentTimeMillis();

	private long CACHE_TIME_TO_LIVE = TimeUnit.DAYS.toMillis(1461L);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletResponse httpResponse = (HttpServletResponse) response;

		httpResponse.setHeader("Cache-Control",
				"max-age=" + CACHE_TIME_TO_LIVE + ", public");
		httpResponse.setHeader("Pragma", "cache");

		// Setting Expires header, for proxy caching
		httpResponse.setDateHeader("Expires",
				CACHE_TIME_TO_LIVE + System.currentTimeMillis());

		// Setting the Last-Modified header, for browser caching
		httpResponse.setDateHeader("Last-Modified", LAST_MODIFIED);

		chain.doFilter(request, response);
	}
}