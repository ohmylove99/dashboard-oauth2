package org.octopus.dashboard.config.web;

import org.octopus.dashboard.service.CustomUserDetailsService;
import org.octopus.dashboard.shared.security.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
		httpSecurity.authorizeRequests().antMatchers("/**").permitAll();
		// static resources
		httpSecurity.authorizeRequests().antMatchers("/css/**", "/js/**", "/images/**",
				"/resources/**", "/webjars/**").permitAll();

		httpSecurity.authorizeRequests().antMatchers("/signin").anonymous().anyRequest()
				.authenticated().and().formLogin().loginPage("/signin")
				.loginProcessingUrl("/sign-in-process.html").failureUrl("/signin?error")
				.usernameParameter("username").passwordParameter("password")
				.defaultSuccessUrl("/admin/dashboard.html", true).and().logout()
				.logoutSuccessUrl("/signin?logout");

		httpSecurity.exceptionHandling().accessDeniedPage("/admin/dashboard.html");
		httpSecurity.sessionManagement().invalidSessionUrl("/signin");

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	}

	@Bean
	public CustomPasswordEncoder passwordEncoder() {
		return new CustomPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}
