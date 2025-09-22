package kr.skeletonlibrary.skeletonlibraryexcel.excel.annotation

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ExcelColumn(
	val headerName: String = "",
	val width: Int = 4000,
)
