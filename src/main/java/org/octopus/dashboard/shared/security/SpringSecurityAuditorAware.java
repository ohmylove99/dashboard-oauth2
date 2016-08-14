package org.octopus.dashboard.shared.security;

import org.octopus.dashboard.config.ConfigConstants;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

	@Override
	public String getCurrentAuditor() {
		String userName = SecurityUtils.getCurrentLogin();
		return (userName != null ? userName : ConfigConstants.SYSTEM_ACCOUNT);
	}
}
