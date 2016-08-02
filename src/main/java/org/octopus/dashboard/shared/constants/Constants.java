package org.octopus.dashboard.shared.constants;

import java.nio.charset.Charset;

public final class Constants {

	private Constants() {
		// hide me
	}

	public static final Charset UTF8 = Charset.forName("UTF-8");

	/**
	 * Assets Version constant
	 */
	public static final String ASSETS_VERSION = "assetsVersion";
	/**
	 * The name of the ResourceBundle used in this application
	 */
	public static final String BUNDLE_KEY = "ApplicationResources";

	/**
	 * File separator from System properties
	 */
	public static final String FILE_SEP = System.getProperty("file.separator");

	/**
	 * User home from System properties
	 */
	public static final String USER_HOME = System.getProperty("user.home") + FILE_SEP;

	/**
	 * The name of the configuration hashmap stored in application scope.
	 */
	public static final String CONFIG = "appConfig";

	/**
	 * The name of the Administrator role, as specified in web.xml
	 */
	public static final String ADMIN_ROLE = "ROLE_ADMIN";

	/**
	 * The name of the User role, as specified in web.xml
	 */
	public static final String USER_ROLE = "ROLE_USER";

	/**
	 * The name of the user's role list, a request-scoped attribute when
	 * adding/editing a user.
	 */
	public static final String USER_ROLES = "userRoles";

	/**
	 * The name of the available roles list, a request-scoped attribute when
	 * adding/editing a user.
	 */
	public static final String AVAILABLE_ROLES = "availableRoles";

	/**
	 * key mapping in table for user/roles
	 */
	public static final Long Supper_User_Key = 1L;
	public static final Long Role_Admin_Key = 1L;
	public static final Long Role_User_Key = 2L;
	public static final Long Role_Readonly_Key = 3L;

}
