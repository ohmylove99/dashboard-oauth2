package org.octopus.dashboard.config;

import javax.inject.Inject;

import org.octopus.dashboard.service.CustomUserDetailsService;
import org.octopus.dashboard.shared.security.AjaxLogoutSuccessHandler;
import org.octopus.dashboard.shared.security.AuthoritiesConstants;
import org.octopus.dashboard.shared.security.CustomAccessDeniedHandler;
import org.octopus.dashboard.shared.security.CustomPasswordEncoder;
import org.octopus.dashboard.shared.security.Http401UnauthorizedEntryPoint;
import org.octopus.dashboard.shared.web.filter.CsrfCookieGeneratorFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	private static final Logger logger = LoggerFactory
			.getLogger(WebSecurityConfiguration.class);
	@Inject
    private AppProperties appProperties;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	/*
	 * @Inject private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;
	 */

	@Inject
	private Http401UnauthorizedEntryPoint authenticationEntryPoint;

	/*
	 * @Inject private RememberMeServices rememberMeServices;
	 */

	@Inject
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**")
				.antMatchers("/app/**/*.{js,html}").antMatchers("/bower_components/**")
				.antMatchers("/i18n/**").antMatchers("/content/**")
				.antMatchers("/swagger-ui/index.html").antMatchers("/test/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http
        .sessionManagement()
        .maximumSessions(32) // maximum number of concurrent sessions for one user
        //.sessionRegistry(sessionRegistry)
        .and().and()
        .csrf()
        .ignoringAntMatchers("/websocket/**")
    .and()
        .addFilterAfter(new CsrfCookieGeneratorFilter(), CsrfFilter.class)
        .exceptionHandling()
        .accessDeniedHandler(new CustomAccessDeniedHandler())
        .authenticationEntryPoint(authenticationEntryPoint)
    .and()
        .rememberMe()
        //.rememberMeServices(rememberMeServices)
        .rememberMeParameter("remember-me")
        .key(appProperties.getSecurity().getRememberme().getKey())
    .and()
        .formLogin()
        .loginProcessingUrl("/api/authentication")
        //.successHandler(ajaxAuthenticationSuccessHandler)
        //.failureHandler(ajaxAuthenticationFailureHandler)
        .usernameParameter("j_username")
        .passwordParameter("j_password")
        .permitAll()
    .and()
        .logout()
        .logoutUrl("/api/logout")
        //.logoutSuccessHandler(ajaxLogoutSuccessHandler)
        .deleteCookies("JSESSIONID", "CSRF-TOKEN", "hazelcast.sessionId")
        .permitAll()
    .and()
        .headers()
        .frameOptions()
        .disable()
    .and()
        .authorizeRequests()
        .antMatchers("/api/register").permitAll()
        .antMatchers("/api/activate").permitAll()
        .antMatchers("/api/authenticate").permitAll()
        .antMatchers("/api/account/reset_password/init").permitAll()
        .antMatchers("/api/account/reset_password/finish").permitAll()
        .antMatchers("/api/profile-info").permitAll()
        .antMatchers("/api/**").authenticated()
        .antMatchers("/websocket/tracker").hasAuthority(AuthoritiesConstants.ADMIN)
        .antMatchers("/websocket/**").permitAll()
        .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
        .antMatchers("/v2/api-docs/**").permitAll()
        .antMatchers("/swagger-resources/configuration/ui").permitAll()
        .antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.ADMIN);

	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
		return new SecurityEvaluationContextExtension();
	}

	@Bean
	public CustomPasswordEncoder passwordEncoder() {
		return new CustomPasswordEncoder();
	}
}
