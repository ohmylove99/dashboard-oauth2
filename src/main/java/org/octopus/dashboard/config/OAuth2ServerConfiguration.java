package org.octopus.dashboard.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.octopus.dashboard.service.CustomUserDetailsService;
import org.octopus.dashboard.shared.security.AjaxLogoutSuccessHandler;
import org.octopus.dashboard.shared.security.AuthoritiesConstants;
import org.octopus.dashboard.shared.security.Http401UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class OAuth2ServerConfiguration {

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration
			extends ResourceServerConfigurerAdapter {

		@Inject
		private Http401UnauthorizedEntryPoint authenticationEntryPoint;

		@Inject
		private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
					.and().logout().logoutUrl("/api/logout")
					.logoutSuccessHandler(ajaxLogoutSuccessHandler).and().csrf()
					.requireCsrfProtectionMatcher(
							new AntPathRequestMatcher("/oauth/authorize"))
					.disable().headers().frameOptions().disable().and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
					.authorizeRequests().antMatchers("/api/authenticate").permitAll()
					.antMatchers("/api/register").permitAll().antMatchers("/api/logs/**")
					.hasAnyAuthority(AuthoritiesConstants.ADMIN).antMatchers("/api/**")
					.authenticated().antMatchers("/metrics/**")
					.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/health/**")
					.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/trace/**")
					.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/dump/**")
					.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/shutdown/**")
					.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/beans/**")
					.hasAuthority(AuthoritiesConstants.ADMIN)
					.antMatchers("/configprops/**")
					.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/info/**")
					.hasAuthority(AuthoritiesConstants.ADMIN)
					.antMatchers("/autoconfig/**")
					.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/env/**")
					.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/trace/**")
					.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/api-docs/**")
					.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/protected/**")
					.authenticated();

		}
	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration
			extends AuthorizationServerConfigurerAdapter implements EnvironmentAware {
		private TokenStore tokenStore = new InMemoryTokenStore();
		private static final String ENV_OAUTH = "authentication.oauth.";
		private static final String PROP_CLIENTID = "clientid";
		private static final String PROP_SECRET = "secret";
		private static final String PROP_TOKEN_VALIDITY_SECONDS = "tokenValidityInSeconds";

		private RelaxedPropertyResolver propertyResolver;

		@Inject
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;
		@Autowired
		private CustomUserDetailsService userDetailsService;

		@Autowired
		private DataSource dataSource;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {

			endpoints.tokenStore(this.tokenStore)
					.authenticationManager(authenticationManager)
					.userDetailsService(userDetailsService);
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.inMemory()
					.withClient(propertyResolver.getProperty(PROP_CLIENTID))
					.scopes("read", "write")
					.authorities(AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER)
					.authorizedGrantTypes("password", "refresh_token")
					.secret(propertyResolver.getProperty(PROP_SECRET))
					.accessTokenValiditySeconds(propertyResolver.getProperty(
							PROP_TOKEN_VALIDITY_SECONDS, Integer.class, 1800));
		}

		@Override
		public void setEnvironment(Environment environment) {
			this.propertyResolver = new RelaxedPropertyResolver(environment, ENV_OAUTH);
		}

		@Bean
		@Primary
		public DefaultTokenServices tokenServices() {
			DefaultTokenServices tokenServices = new DefaultTokenServices();
			tokenServices.setSupportRefreshToken(true);
			tokenServices.setTokenStore(this.tokenStore);
			return tokenServices;
		}

		@Bean
		public JdbcTokenStore tokenStore() {
			return new JdbcTokenStore(dataSource);
		}

		@Bean
		protected AuthorizationCodeServices authorizationCodeServices() {
			return new JdbcAuthorizationCodeServices(dataSource);
		}
	}

}
