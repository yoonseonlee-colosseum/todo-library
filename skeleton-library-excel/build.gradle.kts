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
			version = "0.0.5"
		}
	}
}

dependencies {
	api("kr.skeleton:skeleton-library-shared:0.0.3")
	implementation(libs.poi.ooxml)
}

tasks.withType<Jar> {
	enabled = true
}

tasks.withType<BootJar> {
	enabled = false
}
