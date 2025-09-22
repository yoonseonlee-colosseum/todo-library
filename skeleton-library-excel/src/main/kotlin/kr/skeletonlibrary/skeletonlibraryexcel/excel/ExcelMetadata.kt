package kr.skeletonlibrary.skeletonlibraryexcel.excel

data class ExcelMetadata(
	val excelFileName: String,
	val excelHeaderName: Map<String, String>,
	val parentHeaderNames: List<String?>,
	val dataFieldInfo: Map<String, FieldInfo>,
) {
	fun getExcelHeaderName(dataFieldName: String): String? = excelHeaderName[dataFieldName]

	fun isIncludeParentHeader(): Boolean = parentHeaderNames.any { it != null }
}

data class FieldInfo(
	val dataFieldName: String,
	val dataFieldWidth: Int,
)
