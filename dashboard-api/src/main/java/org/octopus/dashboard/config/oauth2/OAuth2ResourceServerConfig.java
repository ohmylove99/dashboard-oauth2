package org.octopus.dashboard.config.oauth2;

import org.apache.commons.lang3.BooleanUtils;
import org.octopus.dashboard.config.AppProperties;
import org.octopus.dashboard.shared.security.AjaxLogoutSuccessHandler;
import org.octopus.dashboard.shared.security.AuthoritiesConstants;
import org.octopus.dashboard.shared.security.Http401UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@SuppressWarnings("unused")
	@Autowired
	private Environment env;

	@Autowired
	private AppProperties appProperties;
	@Autowired
	private Http401UnauthorizedEntryPoint authenticationEntryPoint;
	@Autowired
	private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
			.and()
				.logout().logoutUrl("/api/logout").logoutSuccessHandler(ajaxLogoutSuccessHandler)
/*			.and()
				.csrf()
				.requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize")).disable().headers().frameOptions().disable()*/
			.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.authorizeRequests().antMatchers("/api/authenticate").permitAll()
				.antMatchers("/api/register").permitAll()
				.antMatchers("/api/logs/**").hasAnyAuthority(AuthoritiesConstants.ADMIN)
				.antMatchers("/api/**").authenticated()
				.antMatchers("/metrics/**").hasAuthority(AuthoritiesConstants.ADMIN)
				.antMatchers("/health/**").hasAuthority(AuthoritiesConstants.ADMIN)
				.antMatchers("/trace/**").hasAuthority(AuthoritiesConstants.ADMIN)
				.antMatchers("/dump/**").hasAuthority(AuthoritiesConstants.ADMIN)
				.antMatchers("/shutdown/**").hasAuthority(AuthoritiesConstants.ADMIN)
				.antMatchers("/beans/**").hasAuthority(AuthoritiesConstants.ADMIN)
				.antMatchers("/configprops/**").hasAuthority(AuthoritiesConstants.ADMIN)
				.antMatchers("/info/**").hasAuthority(AuthoritiesConstants.ADMIN)
				.antMatchers("/autoconfig/**").hasAuthority(AuthoritiesConstants.ADMIN)
				.antMatchers("/env/**").hasAuthority(AuthoritiesConstants.ADMIN)
				.antMatchers("/trace/**").hasAuthority(AuthoritiesConstants.ADMIN)
				.antMatchers("/api-docs/**").hasAuthority(AuthoritiesConstants.ADMIN)
				.antMatchers("/protected/**").authenticated();
		//@formatter:on
		// if enable OAuth
		if (BooleanUtils.toBoolean(appProperties.getSecurity().getOauthEnable())) {
			http.authorizeRequests().antMatchers("/api/**").authenticated();
		}
		else {
			http.authorizeRequests().antMatchers("/api/**").permitAll();
		}
	}
	//@formatter:off
	// Remote token service
	/*
	 * @Primary
	 * 
	 * @Bean public RemoteTokenServices tokenService() { final RemoteTokenServices
	 * tokenService = new RemoteTokenServices(); tokenService.setCheckTokenEndpointUrl(
	 * "http://localhost:8081/spring-security-oauth-server/oauth/check_token");
	 * tokenService.setClientId("fooClientIdPassword");
	 * tokenService.setClientSecret("secret"); return tokenService; }
	 */

	// JWT token store
	
   /* @Override
    public void configure(final ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices());
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // converter.setSigningKey("123");
        final Resource resource = new ClassPathResource("public.txt");
        String publicKey = null;
        try {
            publicKey = IOUtils.toString(resource.getInputStream());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }*/
	//@formatter:on
	// JDBC token store configuration

	/*
	 * @Bean public DataSource dataSource() { final DriverManagerDataSource dataSource =
	 * new DriverManagerDataSource();
	 * dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
	 * dataSource.setUrl(env.getProperty("jdbc.url"));
	 * dataSource.setUsername(env.getProperty("jdbc.user"));
	 * dataSource.setPassword(env.getProperty("jdbc.pass")); return dataSource; }
	 *
	 * @Bean public TokenStore tokenStore() { return new JdbcTokenStore(dataSource()); }
	 */
}
