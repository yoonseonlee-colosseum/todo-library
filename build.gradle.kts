import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm")
	kotlin("kapt")
	kotlin("plugin.spring") apply false
	id("org.springframework.boot") apply false
	id("io.spring.dependency-management") apply false
	id("org.jlleitschuh.gradle.ktlint")
	`maven-publish`
	`java-library`
}

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

val projectGroup: String by project
val applicationVersion: String by project

allprojects {
	group = projectGroup
	version = applicationVersion

	repositories {
		mavenCentral()
		maven {
			name = "GitHubPackages"
			url = uri("https://maven.pkg.github.com/yoonseonlee-colosseum/todo-library")
			credentials {
				username = rootProject.findProperty("gpr.user") as String
				password = rootProject.findProperty("gpr.key") as String
			}
		}
	}
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.kapt")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.jlleitschuh.gradle.ktlint")

	dependencies {
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	}

	plugins.withId("maven-publish") {
		extensions.configure<PublishingExtension>("publishing") {
			repositories {
				maven {
					name = "GitHubPackages"
					url = uri("https://maven.pkg.github.com/yoonseonlee-colosseum/todo-library")
					credentials {
						username = rootProject.findProperty("gpr.user") as String
						password = rootProject.findProperty("gpr.key") as String
					}
				}
			}
		}
	}

//	publishing {
//		repositories {
//			maven {
//				name = "GitHubPackages"
//				url = uri("https://maven.pkg.github.com/yoonseonlee-colosseum/todo-library")
//				credentials {
//					username = rootProject.findProperty("gpr.user") as String
//					password = rootProject.findProperty("gpr.key") as String
//				}
//			}
//		}
//	}

	tasks.withType<KotlinCompile> {
		compilerOptions {
			freeCompilerArgs.set(listOf("-Xjsr305=strict"))
			jvmTarget.set(JvmTarget.JVM_21)
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
