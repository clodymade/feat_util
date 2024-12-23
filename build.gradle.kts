plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    `maven-publish`
}

android {
    namespace = "com.mwkg.util"
    compileSdk = 35
    ndkVersion = "25.1.8937393"
    buildToolsVersion = "35.0.0"

    defaultConfig {
        minSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    lint {
        targetSdk = 35
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    // AndroidX core libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Test libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// Add Maven publishing configuration for JitPack compatibility
publishing {
    publications {
        create<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"]) // Ensure the release component is available
            }

            // Define Maven artifact metadata
            groupId = "com.github.clodymade" // GitHub username as group ID
            artifactId = "feat_util"         // Module name as artifact ID
            version = "1.0.3"                // Version matching the Git tag

            // Configure POM metadata
            pom {
                name.set("feat_util")
                description.set("A utility library for Android apps.")
                url.set("https://github.com/clodymade/feat_util")

                licenses {
                    license {
                        name.set("MIT License") // License type
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("clodymade")
                        name.set("netcanis")
                        email.set("netcanis@gmail.com")
                    }
                }

                scm {
                    // Source Control Management (SCM) details
                    connection.set("scm:git:git://github.com/clodymade/feat_util.git")
                    developerConnection.set("scm:git:ssh://git@github.com:clodymade/feat_util.git")
                    url.set("https://github.com/clodymade/feat_util")
                }
            }
        }
    }
}
