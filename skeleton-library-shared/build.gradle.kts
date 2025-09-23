import org.springframework.boot.gradle.tasks.bundling.BootJar

description = "skeleton-library-shared"

plugins {
	`java-test-fixtures`
	`maven-publish`
	`java-library`
}

publishing {
	publications {
		create<MavenPublication>("gpr") {
			from(components["java"]) // 어떤 빌드 결과물을 아티팩트로 삼을지 지정
			groupId = "kr.skeleton"
			artifactId = "skeleton-library-shared"
			version = libs.versions.skeleton.library.shared.get()
			// libs.versions.toml에 정의된 버전을 그대로 사용하여 publish 버전과 의존성 버전을 일치시킴
			// → 같은 리포지토리 내 모듈들이 항상 동일한 버전을 참조하도록 관리 포인트를 한 곳으로 통일
		}
	}
}

tasks.withType<Jar> {
	enabled = true
}

tasks.withType<BootJar> {
	enabled = false
}
