package org.octopus.dashboard.config.oauth2;

import javax.sql.DataSource;

import org.octopus.dashboard.service.CustomUserDetailsService;
import org.octopus.dashboard.shared.security.AuthoritiesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter
		implements EnvironmentAware {

	private static final String ENV_OAUTH = "authentication.oauth.";
	private static final String PROP_CLIENTID = "clientid";
	private static final String PROP_SECRET = "secret";
	private static final String PROP_TOKEN_VALIDITY_SECONDS = "tokenValidityInSeconds";

	@Autowired
	private Environment env;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	private RelaxedPropertyResolver propertyResolver;

	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private DataSource dataSource;

	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints)
			throws Exception {
		// @formatter:off
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		//tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
		endpoints.tokenStore(tokenStore())
				// .accessTokenConverter(accessTokenConverter())
				.tokenEnhancer(tokenEnhancerChain).authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService);
		// @formatter:on
	}

	@Bean
	protected AuthorizationCodeServices authorizationCodeServices() {
		return new JdbcAuthorizationCodeServices(dataSource);
	}

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer)
			throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
		// @formatter:off
		clients
				// .jdbc(dataSource())
				.inMemory().withClient("sampleClientId").authorizedGrantTypes("implicit")
				.scopes("read", "write", "foo", "bar").autoApprove(false).accessTokenValiditySeconds(3600)

				.and()
				.withClient(propertyResolver.getProperty(PROP_CLIENTID))
				.scopes("read", "write")
				.authorities(AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER)
				.authorizedGrantTypes("password", "refresh_token")
				.secret(propertyResolver.getProperty(PROP_SECRET))
				.accessTokenValiditySeconds(propertyResolver.getProperty(PROP_TOKEN_VALIDITY_SECONDS, Integer.class, 1800)) // 30 days

				.and().withClient("barClientIdPassword").secret("secret")
				.authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes("bar", "read", "write")
				.accessTokenValiditySeconds(3600) // 1 hour
				.refreshTokenValiditySeconds(2592000) // 30 days
		;
		// @formatter:on
	}

	@Bean
	public InMemoryTokenStore tokenStore() {
		return new InMemoryTokenStore();
	}
	// @formatter:off
	/*@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JdbcTokenStore tokenStore() {
		return new JdbcTokenStore(dataSource());
	}*/

	/*@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		// converter.setSigningKey("123");
		final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
				new ClassPathResource("keystore.jks"), "mypass".toCharArray());
		converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
		return converter;
	}*/
	// @formatter:on

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setTokenStore(tokenStore());
		return tokenServices;
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment, ENV_OAUTH);
	}
	// JDBC token store configuration

	/*
	 * @Bean public DataSourceInitializer dataSourceInitializer(final DataSource
	 * dataSource) { final DataSourceInitializer initializer = new
	 * DataSourceInitializer(); initializer.setDataSource(dataSource);
	 * initializer.setDatabasePopulator(databasePopulator()); return initializer; }
	 * 
	 * private DatabasePopulator databasePopulator() { final ResourceDatabasePopulator
	 * populator = new ResourceDatabasePopulator(); populator.addScript(schemaScript);
	 * return populator; }
	 * 
	 * @Bean public DataSource dataSource() { final DriverManagerDataSource dataSource =
	 * new DriverManagerDataSource();
	 * dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
	 * dataSource.setUrl(env.getProperty("jdbc.url"));
	 * dataSource.setUsername(env.getProperty("jdbc.user"));
	 * dataSource.setPassword(env.getProperty("jdbc.pass")); return dataSource; }
	 * 
	 */

}
