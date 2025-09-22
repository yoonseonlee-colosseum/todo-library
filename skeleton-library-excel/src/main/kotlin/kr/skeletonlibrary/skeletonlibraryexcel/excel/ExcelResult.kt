package kr.skeletonlibrary.skeletonlibraryexcel.excel

import java.io.OutputStream

data class ExcelResult(
	val fileName: String,
	val bytes: ByteArray,
) {
	fun writeTo(outputStream: OutputStream) {
		outputStream.write(bytes)
	}
}
