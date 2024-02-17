import org.jetbrains.kotlin.gradle.dsl.JvmTarget

/**
 * This file was largely based on the build file used for kotlinx.html, which can be found at:
 *
 * https://github.com/Kotlin/kotlinx.html/blob/6c926dda0567d765fe84239e13606e43ff2e3657/build.gradle.kts
 */

plugins {
    kotlin("jvm") version "1.9.22"
    id("signing")
    id("maven-publish")
    id("cl.franciscosolis.sonatype-central-upload") version "1.0.2"
    id("co.uzzu.dotenv.gradle") version "4.0.0"
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

group = "eu.majortanya"
version = "1.0-alpha02"
description = "A Kotlin reimplementation of gpanther/java-nat-sort, now for Java 8+"


dependencies {
    testImplementation("junit:junit:4.13.2")
    implementation(kotlin("stdlib-jdk8:1.9.22"))
}

kotlin {
    target {
        mavenPublication {
            groupId = project.group as String
        }
    }

    jvmToolchain(8)

    compilerOptions {
        jvmTarget by JvmTarget.fromTarget(JavaVersion.VERSION_1_8.toString())
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.withType<GenerateModuleMetadata> {
    enabled = true
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            pom.config()
            from(components["kotlin"])
        }
    }
}

tasks.register("copyDefaultPomToRoot") {
    dependsOn("generateMetadataFileForMavenPublication", "generatePomFileForMavenPublication")

    file("build/publications/maven/pom-default.xml").copyTo(file("pom.xml"), overwrite = true)
}

tasks.register("cleanSonatypeDir") {
    delete("build/sonatype-central-upload")
}

tasks.sonatypeCentralUpload {
    dependsOn("javadocJar", "sourcesJar", "kotlinSourcesJar", "copyDefaultPomToRoot", "cleanSonatypeDir")

    username = env.PUBLISHING_USER.value
    password = env.PUBLISHING_PASSWORD.value

    archives = files(
        "build/libs/${project.name}-${project.version}.jar",
        "build/libs/${project.name}-${project.version}-javadoc.jar",
        "build/libs/${project.name}-${project.version}-sources.jar",
    )
    pom = file("pom.xml")

    signingKey = env.SIGNING_PRIVATEKEY.value
    signingKeyPassphrase = env.SIGNING_PASSPHRASE.value
    publicKey = env.SIGNING_PUBLICKEY.value
}

fun MavenPom.config(config: MavenPom.() -> Unit = {}) {
    config()

    url by "https://github.com/MajorTanya/kotlin-natural-sort"
    name by "kotlin-natural-sort"
    description by project.description

    licenses {
        license {
            name by "The Apache License, Version 2.0"
            url by "https://www.apache.org/licenses/LICENSE-2.0.txt"
        }
    }

    scm {
        connection by "scm:git:git@github.com:MajorTanya/kotlin-natural-sort.git"
        url by "https://github.com/MajorTanya/kotlin-natural-sort"
        tag by "HEAD"
    }

    developers {
        developer {
            name by "MajorTanya"
            roles to "developer"
        }
    }
}

infix fun <T> Property<T>.by(value: T) {
    set(value)
}
