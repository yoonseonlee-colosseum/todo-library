package kr.skeletonlibrary.skeletonlibraryshared.util

import kr.skeletonlibrary.skeletonlibraryshared.util.clock.DateProvider
import kr.skeletonlibrary.skeletonlibraryshared.util.clock.SystemDateProvider
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.nio.file.Paths
import java.time.format.DateTimeFormatter

object FilenameUtil {
	private const val DATETIME_FORMAT = "yyMMdd"
	private const val EXTENSION_DELIMITER = '.'
	private const val UNDER_HYPHEN = "_"
	private const val HYPHEN = "-"
	private const val SPACE_ENCODED_PLUS = "+"
	private const val SPACE_ENCODED_PERCENT_20 = "%20"

	fun encode(fileName: String): String =
		URLEncoder
			.encode(fileName, StandardCharsets.UTF_8.name())
			.replace(SPACE_ENCODED_PLUS, SPACE_ENCODED_PERCENT_20)

	fun removeExtension(fileName: String): String {
		val lastDotIndex = fileName.lastIndexOf(EXTENSION_DELIMITER)
		if (lastDotIndex == -1) {
			return fileName // 확장자 없음
		}
		return fileName.substring(0, lastDotIndex)
	}

	fun getExtension(fileName: String): String {
		val lastDotIndex = fileName.lastIndexOf(EXTENSION_DELIMITER)
		if (lastDotIndex == -1) {
			return fileName // 확장자 없음
		}
		return fileName.substring(lastDotIndex + 1)
	}

	fun appendSuffixWithDate(
		fileName: String,
		suffix: String?,
		clockProvider: DateProvider,
	): String {
		val result = StringBuilder(removeExtension(fileName))
		if (!suffix.isNullOrBlank()) {
			result.append(UNDER_HYPHEN).append(suffix)
		}
		result
			.append(HYPHEN)
			.append(
				clockProvider
					.nowDate()
					.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT)),
			)
		return result.toString()
	}

	fun extractFileName(path: String): String = Paths.get(path).fileName.toString()

	fun toEncodedFileNameWithDate(path: String): String {
		val fileName = extractFileName(path)
		val baseName = removeExtension(fileName)
		val suffixed = appendSuffixWithDate(baseName)
		return encode(suffixed)
	}

	fun appendSuffixWithDate(
		fileName: String,
		suffix: String? = null,
	): String = appendSuffixWithDate(fileName, suffix, SystemDateProvider())
}
