import org.springframework.boot.gradle.tasks.bundling.BootJar

description = "skeleton-library-excel"

plugins {
	`maven-publish`
	`java-library`
}

publishing {
	publications {
		create<MavenPublication>("gpr") {
			from(components["java"]) // 어떤 빌드 결과물을 아티팩트로 삼을지 지정
			groupId = "kr.skeleton"
			artifactId = "skeleton-library-excel"
			version = "0.0.2"
		}
	}
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

dependencies {
	implementation(project(":skeleton-library-shared"))
	implementation(libs.poi.ooxml)
}

tasks.withType<Jar> {
	enabled = true
}

tasks.withType<BootJar> {
	enabled = false
}
