# Dashboard Spring Boot Sample - Core

Spring Integration
==================

# Checking out and Building

To check out the project and build from source, do the following:

    git clone git://github.com/ohmylove99/dashboard-oauth2
    cd dashboard-oauth2
    ./gradlew build

**NOTE:** Java 8 compiler is required to build the project.

If you encounter out of memory errors during the build, increase available heap and permgen for Gradle:

    GRADLE_OPTS='-XX:MaxPermSize=1024m -Xmx1024m'

To build and install jars into your local Maven cache:

    ./gradlew install

To build api Javadoc (results will be in `build/api`):

    ./gradlew api

To build reference documentation (results will be in `build/reference`):

    ./gradlew reference

To build complete distribution including `-dist`, `-docs`, and `-schema` zip files (results will be in `build/distributions`)

    ./gradlew dist

# Using Eclipse


# Resources

For more information, please visit the website at:

