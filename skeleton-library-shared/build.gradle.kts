import org.springframework.boot.gradle.tasks.bundling.BootJar

description = "skeleton-library-shared"

plugins {
	`java-test-fixtures`
}

tasks.withType<Jar> {
	enabled = true
}

tasks.withType<BootJar> {
	enabled = false
}
