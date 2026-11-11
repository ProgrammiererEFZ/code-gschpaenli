import org.jetbrains.intellij.platform.gradle.models.ProductRelease
import org.jetbrains.intellij.platform.gradle.tasks.RunIdeTask

plugins {
    java
    id("org.jetbrains.intellij.platform") version "2.20.1"
    // Supplies <change-notes> from CHANGELOG.md and the getChangelog / patchChangelog tasks used by the workflows.
    id("org.jetbrains.changelog") version "2.5.0"
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()

tasks.withType<JavaCompile>().configureEach {
    options.release.set(21)
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Optional path to a locally installed IDE (-PlocalIde=... or ~/.gradle/gradle.properties). Without it, Gradle downloads
// IntelliJ IDEA Community `platformVersion`, which is what CI does.
val localIde = providers.gradleProperty("localIde")

dependencies {
    intellijPlatform {
        if (localIde.isPresent) {
            local(localIde)
        } else {
            intellijIdeaCommunity(providers.gradleProperty("platformVersion"))
        }
    }

    // Plain JUnit 5 unit tests for the IDE-independent code (the simulation in the `world` package).
    testImplementation(platform("org.junit:junit-bom:6.1.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // The `test` task runs with the IDE's class loader and classpath, which references JUnit 4 classes
    // (NoClassDefFoundError: org/junit/rules/TestRule without it). Workaround from the IntelliJ Platform Gradle Plugin
    // FAQ, issue IJPL-159134.
    testRuntimeOnly("junit:junit:4.13.2")
}

intellijPlatform {
    // Searchable options only make the settings page findable via "Search Everywhere"; building them launches a
    // headless IDE, so it is skipped to keep builds fast.
    buildSearchableOptions = false
    instrumentCode = false

    pluginConfiguration {
        version = providers.gradleProperty("pluginVersion")
        ideaVersion {
            sinceBuild = "252"
            untilBuild = provider { null }
        }
        // <change-notes> is filled automatically from CHANGELOG.md by the org.jetbrains.changelog integration:
        // the section of the current version, or [Unreleased] when there is none.
    }

    // Since IntelliJ Platform Gradle Plugin 2.14 these are also the defaults; spelled out for clarity.
    signing {
        certificateChain = providers.environmentVariable("CERTIFICATE_CHAIN")
        privateKey = providers.environmentVariable("PRIVATE_KEY")
        password = providers.environmentVariable("PRIVATE_KEY_PASSWORD")
    }

    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
        // A pre-release version like 1.2.0-beta.1 goes to the "beta" channel, everything else to "default".
        // Custom channels: https://plugins.jetbrains.com/docs/marketplace/custom-release-channels.html
        channels = providers.gradleProperty("pluginVersion").map {
            listOf(it.substringAfter('-', "").substringBefore('.').ifEmpty { "default" })
        }
    }

    pluginVerification {
        ides {
            val localVerifierIdes = providers.gradleProperty("localVerifierIdes")
            if (localVerifierIdes.isPresent) {
                localVerifierIdes.get().split(',').map { it.trim() }.filter { it.isNotEmpty() }.forEach { local(it) }
            } else {
                // The IDE the plugin is compiled against (lowest supported version) ...
                current()
                // ... and the newest stable IntelliJ IDEA release.
                latest {
                    channels = listOf(ProductRelease.Channel.RELEASE)
                }
            }
        }
    }
}

changelog {
    groups = emptyList()
    repositoryUrl = providers.gradleProperty("pluginRepositoryUrl")
    // Git tags are "v<version>" (created by .github/workflows/build.yml); the compare links in CHANGELOG.md use the
    // same prefix, so they resolve on GitHub.
    versionPrefix = "v"
}

// Extra sandbox targets to try the plugin in other locally installed IDEs. Registered only when the IDE exists,
// so CI and other machines are unaffected. Override the paths with -PphpStormPath=... / -PpyCharmPath=...
val localRunTargets = mapOf(
    "runPhpStorm" to providers.gradleProperty("phpStormPath").orElse("C:/Program Files/JetBrains/PhpStorm 2026.2.3"),
    // Must be PyCharm 2025.2 or newer (sinceBuild 252); 2025.1 would not load the plugin.
    "runPyCharm" to providers.gradleProperty("pyCharmPath").orElse("C:/Program Files/JetBrains/PyCharm 2025.2"),
)
intellijPlatformTesting {
    runIde {
        localRunTargets.forEach { (taskName, path) ->
            val ideDir = file(path.get())
            if (ideDir.isDirectory) {
                register(taskName) {
                    localPath = ideDir
                }
            }
        }
    }
}

// Development helpers for the sandbox IDE:
//   ./gradlew runIde -PopenProject=<dir>          opens a project directly
//   ./gradlew runIde -PpetsDebugDir=<dir>         writes periodic window snapshots (PNG) + surface dumps there
//   ./gradlew runIde -PpetsPerfLog=<file>         runs an ~11 minute benchmark (pets off / 1 / 6 pets at 30 and 60 fps),
//                                                 appends the results to <file> and closes the IDE (overlay/PerfProbe)
val openProject = providers.gradleProperty("openProject")
val petsDebugDir = providers.gradleProperty("petsDebugDir")
val petsPerfLog = providers.gradleProperty("petsPerfLog")
tasks.withType<RunIdeTask>().configureEach {
    // The lambdas below must capture only these local providers, not the script-level vals: reading a script-level val
    // captures the Gradle script object, which the configuration cache cannot serialize ("BUILD FAILED" after every run).
    val debugDir = petsDebugDir
    val perfLog = petsPerfLog
    val projectToOpen = openProject
    jvmArgumentProviders += CommandLineArgumentProvider {
        buildList {
            add("-Dide.show.tips.on.startup.default.value=false")
            if (debugDir.isPresent) {
                add("-Dcodegschpaenli.debug.snapshotDir=${debugDir.get()}")
                add("-Dcodegschpaenli.debug.pets=6")
                add("-Didea.trust.all.projects=true")
            }
            if (perfLog.isPresent) {
                add("-Dcodegschpaenli.perf.log=${perfLog.get()}")
                add("-Didea.trust.all.projects=true")
            }
        }
    }
    argumentProviders += CommandLineArgumentProvider {
        if (projectToOpen.isPresent) listOf(projectToOpen.get()) else emptyList()
    }
}

tasks.test {
    useJUnitPlatform()
}

// Ship the license and the GPL section 7 notice inside the plugin jar.
tasks.jar {
    from(listOf("LICENSE", "NOTICE")) {
        into("META-INF")
    }
}
