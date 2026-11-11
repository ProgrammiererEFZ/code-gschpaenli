rootProject.name = "code-gschpaenli"

plugins {
    // Lets Gradle download a matching JDK when the Java toolchain requested by the build is not installed.
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
