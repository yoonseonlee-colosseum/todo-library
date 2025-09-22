package kr.skeletonlibrary.skeletonlibraryshared.util

import kr.skeletonlibrary.skeletonlibraryshared.util.clock.FakeDateProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test

class FilenameUtilTest {
	@Test
	@DisplayName("encode 함수가 UTF-8 URL 인코딩된 파일명을 반환한다.")
	fun encodeTest() {
		// given
		val fileName = "테스트.xlsx"
		val encodeFileName = "%ED%85%8C%EC%8A%A4%ED%8A%B8.xlsx" // 직접 인코딩 값

		// when
		val encoded = FilenameUtil.encode(fileName)

		// then
		assertThat(encoded).isEqualTo(encodeFileName)
	}

	@Test
	@DisplayName("encode 함수가 UTF-8 URL 인코딩된 파일명 중 공백을 `+`가 아닌 `%20`로 치환한 파일명으로 반환한다.")
	fun encodeSpaceTest() {
		// given
		val fileName = "테 스 트.xlsx"
		val encodeFileName = "%ED%85%8C%20%EC%8A%A4%20%ED%8A%B8.xlsx"

		// when
		val encoded = FilenameUtil.encode(fileName)

		// then
		assertThat(encoded).isEqualTo(encodeFileName)
	}

	@Test
	@DisplayName("removeExtension 함수가 확장자가 없는 파일명이 주어졌을 때 주어진 파일명을 반환한다.")
	fun removeExtensionNotFoundExtensionTest() {
		// given
		val fileName = "확장자없는파일명"

		// when
		val result = FilenameUtil.removeExtension(fileName)

		// then
		assertThat(result).isEqualTo(fileName)
	}

	@Test
	@DisplayName("removeExtension 함수가 파일명이 주어졌을 때 확장자를 제거한 파일명을 반환한다.")
	fun removeExtensionTest() {
		// given
		val fileName = "확장자있는파일명.txt"

		// when
		val result = FilenameUtil.removeExtension(fileName)

		// then
		assertThat(result).isEqualTo("확장자있는파일명")
	}

	@Test
	@DisplayName("getExtension 함수가 파일명이 주어졌을 때 확장자를 반환한다.")
	fun getExtensionTest() {
		// given
		val fileName = "확장자있는파일명.txt"

		// when
		val result = FilenameUtil.getExtension(fileName)

		// then
		assertThat(result).isEqualTo("txt")
	}

	@Test
	@DisplayName("appendSuffixWithDate 함수가 파일명과 suffix가 주어졌을 때 파일명 + suffix + 현재시간을 조합한 파일명을 반환한다.")
	fun appendSuffixWithTimestampTest() {
		// given
		val fileName = "파일명.txt"

		// when
		val result = FilenameUtil.appendSuffixWithDate(fileName, "실패", FakeDateProvider())

		// then
		assertThat(result).isEqualTo("파일명_실패-991231")
	}

	@Test
	@DisplayName("extractFileName 함수가 주어진 경로에서 추출한 파일명을 반환한다.")
	fun extractFileNameTest() {
		// given
		val path = "/colo/backend/choikangsasuke.md"

		// when
		val result = FilenameUtil.extractFileName(path)

		// then
		assertThat(result).isEqualTo("choikangsasuke.md")
	}
}
