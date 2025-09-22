description = "skeleton-library-shared"

plugins {
	kotlin("jvm")
	`java-library`
	`java-test-fixtures`
}

tasks.withType<Jar> {
	enabled = true
}
