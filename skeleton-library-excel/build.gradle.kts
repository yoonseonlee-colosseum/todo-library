description = "skeleton-library-excel"

dependencies {
	implementation(project(":skeleton-library-shared"))
	implementation("org.apache.poi:poi-ooxml:5.4.1")
}

tasks.withType<Jar> {
	enabled = true
}
