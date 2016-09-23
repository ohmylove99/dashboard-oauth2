package org.octopus.dashboard.config.web;

import javax.inject.Inject;

import org.octopus.dashboard.config.AppProperties;
import org.octopus.dashboard.shared.security.AuthoritiesConstants;
import org.octopus.dashboard.shared.security.CustomAccessDeniedHandler;
import org.octopus.dashboard.shared.security.CustomPasswordEncoder;
import org.octopus.dashboard.shared.security.Http401UnauthorizedEntryPoint;
import org.octopus.dashboard.shared.service.CustomUserDetailsService;
import org.octopus.dashboard.shared.web.filter.CsrfCookieGeneratorFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
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
				.defaultSuccessUrl("/dashboard", true).and().logout()
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
/*@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Inject
	private AppProperties appProperties;

	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Inject
	private Http401UnauthorizedEntryPoint authenticationEntryPoint;

	@Override
	public void configure(WebSecurity web) throws Exception {
		//@formatter:off
		web.ignoring()
				.antMatchers(HttpMethod.OPTIONS, "/**")
				.antMatchers("/resources/**")
				.antMatchers("/app/**.{js,html}")
				.antMatchers("/bower_components/**")
				.antMatchers("/i18n/**")
				.antMatchers("/content/**")
				.antMatchers("/swagger-ui/index.html")
				.antMatchers("/metrics/**")
				.antMatchers("/health/**")
				.antMatchers("/trace/**")
				.antMatchers("/dump/**")
				.antMatchers("/beans/**")
				.antMatchers("/configprops/**")
				.antMatchers("/info/**")
				.antMatchers("/autoconfig/**")
				.antMatchers("/env/**")
				.antMatchers("/test/**");
		//@formatter:on
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		//http.csrf().disable();
			http.headers().frameOptions().disable();

		    http
		        .sessionManagement()
		        .maximumSessions(32) // maximum number of concurrent sessions for one user
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
		        .rememberMeParameter("remember-me")
		        .key(appProperties.getSecurity().getRememberme().getKey())
		    .and().requestMatchers().requestMatchers(request -> "/manage".equals(request.getContextPath()))
            .and().authorizeRequests().antMatchers("/signin").anonymous().anyRequest()
			.authenticated().and().formLogin().loginPage("/signin")
			.loginProcessingUrl("/sign-in-process.html")
			.failureUrl("/signin?error")
			.usernameParameter("username").passwordParameter("password")
			.defaultSuccessUrl("/dashboard", true).and().logout()
			.logoutSuccessUrl("/signin?logout")
		    .and()
		        .headers()
		        .frameOptions()
		        .disable()
		    .and().httpBasic()
		    .and()
		        .authorizeRequests()
		        .antMatchers("/css/**", "/js/**", "/images/**","/img/**", "/resources/**", "/webjars/**").permitAll()
		        .antMatchers("/resources/**").permitAll()
                .antMatchers("/login").permitAll()
		        .antMatchers("/api/account/register").permitAll()
		        .antMatchers("/api/account/activate").permitAll()
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
		    
			http.exceptionHandling().accessDeniedPage("/dashboard");
			http.sessionManagement().invalidSessionUrl("/signin");
		  //@formatter:on
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
}*/