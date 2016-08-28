package org.octopus.dashboard.api.doc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.octopus.dashboard.config.ConfigConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.base.Predicate;
import com.google.common.reflect.TypeResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationCodeGrant;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("!" + ConfigConstants.SPRING_PROFILE_PRODUCTION)
public class SwaggerConfig implements EnvironmentAware {

	private final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);
	private RelaxedPropertyResolver propertyResolver;
	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

	public static final String securitySchemaOAuth2 = "oauth2";
	public static final String authorizationScopeGlobal = "global";
	public static final String authorizationScopeGlobalDesc = "accessEverything";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.any()).paths(internalPaths()).build()
				.securitySchemes(Arrays.asList(authorizationCodeFlow()))
				.securityContexts(Arrays.asList(securityContext()));
		
		/*return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
		        .paths(PathSelectors.any()).build().pathMapping("/")
		        .directModelSubstitute(LocalDate.class, String.class)
		        .genericModelSubstitutes(ResponseEntity.class)
		        .alternateTypeRules(newRule(
		            typeResolver.resolve(DeferredResult.class,
		                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
		            typeResolver.resolve(WildcardType.class)))
		        .useDefaultResponseMessages(false)
		        .globalResponseMessage(RequestMethod.GET,
		        		Arrays.asList((new ResponseMessageBuilder().code(500).message("500 message")
		                .responseModel(new ModelRef("Error")).build())))
		        		.securitySchemes(Arrays.asList(authorizationCodeFlow())).securityContexts(Arrays.asList(securityContext()))
		        .apiInfo(apiInfo());*/

	}

	private Predicate<String> internalPaths() {
		return PathSelectors.regex(DEFAULT_INCLUDE_PATTERN);
	}

	private AuthorizationScope getAuthorizationScope() {
		return new AuthorizationScope(authorizationScopeGlobal, authorizationScopeGlobal);
	}

	/**
	 * One of the 5 or so OAuth2 authorisation flows.
	 *
	 * The Authorization Code or Web server flow is suitable for clients that can interact
	 * with the end-user’s user-agent (typically a Web browser), and that can receive
	 * incoming requests from the authorization server (can act as an HTTP server).
	 *
	 * @return
	 */
	private OAuth authorizationCodeFlow() {

		// Other grant types are: "authorization_code",
		// "refresh_token",
		// "password"
		/*return new OAuth(securitySchemaOAuth2, Arrays.asList(getAuthorizationScope()),
				Arrays.asList(new AuthorizationCodeGrant(
						new TokenRequestEndpoint("/oauth/authorize", "barClientIdPassword",
								"secret"),
						new TokenEndpoint("/oauth/token", "access_code"))));*/
		
		return new OAuth(securitySchemaOAuth2, Arrays.asList(getAuthorizationScope()),
				Arrays.asList(new AuthorizationCodeGrant(
						new TokenRequestEndpoint("/oauth/authorize", "barClientIdPassword",
								"secret"),
						new TokenEndpoint("/oauth/token", "password"))));
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth())
				.forPaths(internalPaths()).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope(
				authorizationScopeGlobal, authorizationScopeGlobalDesc);
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays
				.asList(new SecurityReference(securitySchemaOAuth2, authorizationScopes));
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment, "swagger.");
	}

	@Bean
	SecurityConfiguration security() {
		return new SecurityConfiguration("barClientIdPassword", "secret", "test-app-realm",
				"test-app", "foo", ApiKeyVehicle.HEADER, "api_key", "read,write,foo,bar");
	}
	
	 /* @Bean
	  SecurityConfiguration security() {
	    return new SecurityConfiguration("123456", "test-app-realm", "clientapp", "apiKey");
	  }*/

	  @Bean
	  UiConfiguration uiConfig() {
	    return new UiConfiguration("validatorUrl");
	  }

	  private ApiInfo apiInfo() {
	    ApiInfo apiInfo = new ApiInfo("DSM API", "API for DSM", "1.0.0", "termsOfServiceUrl",
	        "nijo@ndimensionz.com", null, null);
	    return apiInfo;
	  }
	  
	 /* @Autowired
	  private TypeResolver typeResolver;*/
}