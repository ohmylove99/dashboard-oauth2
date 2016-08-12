package org.octopus.dashboard.config;

import org.octopus.dashboard.service.CustomUserDetailsService;
import org.octopus.dashboard.shared.security.CustomPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	private static final Logger logger = LoggerFactory
			.getLogger(WebSecurityConfiguration.class);
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) {
		try {
			auth.inMemoryAuthentication().withUser("user") // #1
					.password("password").roles("USER").and().withUser("admin") // #2
					.password("password").roles("ADMIN", "USER");
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/index", "/user/**", "/about"); // #3
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.authorizeRequests().antMatchers("/configuration/**","/swagger**","/webjars/**","/v2/**").permitAll();
		http.authorizeRequests().antMatchers("/signup", "/about").permitAll() // #4
				.antMatchers("/admin/**").hasRole("ADMIN") // #6
				.antMatchers("/oauth/**").permitAll().anyRequest().authenticated() // 7
				.and().formLogin() // #8
				.failureUrl("/login?error") // #9
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").and()
				// .csrf().and()
				.csrf().disable().rememberMe(); // #5
	}

	@Bean
	public CustomPasswordEncoder passwordEncoder() {
		return new CustomPasswordEncoder();
	}
}
