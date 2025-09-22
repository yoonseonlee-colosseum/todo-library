package kr.skeletonlibrary.skeletonlibraryexcel.excel.annotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ExcelFileName(
	val fileName: String = "",
)
