package org.octopus.dashboard.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public class AppProperties {
	private final CorsConfiguration cors = new CorsConfiguration();
	private final Security security = new Security();
	private final Swagger swagger = new Swagger();

	public CorsConfiguration getCors() {
		return cors;
	}

	public Security getSecurity() {
		return security;
	}

	public Swagger getSwagger() {
		return swagger;
	}

	public static class Async {

	}

	public static class Swagger {

		private String title = "Dashboard API";

		private String description = "Dashboard API documentation";

		private String version = "0.0.1";

		private String termsOfServiceUrl;

		private String contact;

		private String contactName;

		private String contactUrl;

		private String contactEmail;

		private String license;

		private String licenseUrl;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getTermsOfServiceUrl() {
			return termsOfServiceUrl;
		}

		public void setTermsOfServiceUrl(String termsOfServiceUrl) {
			this.termsOfServiceUrl = termsOfServiceUrl;
		}

		public String getContact() {
			return contact;
		}

		public void setContact(String contact) {
			this.contact = contact;
		}

		public String getLicense() {
			return license;
		}

		public void setLicense(String license) {
			this.license = license;
		}

		public String getLicenseUrl() {
			return licenseUrl;
		}

		public void setLicenseUrl(String licenseUrl) {
			this.licenseUrl = licenseUrl;
		}

		public String getContactName() {
			return contactName;
		}

		public void setContactName(String contactName) {
			this.contactName = contactName;
		}

		public String getContactUrl() {
			return contactUrl;
		}

		public void setContactUrl(String contactUrl) {
			this.contactUrl = contactUrl;
		}

		public String getContactEmail() {
			return contactEmail;
		}

		public void setContactEmail(String contactEmail) {
			this.contactEmail = contactEmail;
		}

	}

	public static class Security {

		private final Rememberme rememberme = new Rememberme();

		public Rememberme getRememberme() {
			return rememberme;
		}

		public static class Rememberme {

			@NotNull
			private String key;

			public String getKey() {
				return key;
			}

			public void setKey(String key) {
				this.key = key;
			}
		}
	}
}
