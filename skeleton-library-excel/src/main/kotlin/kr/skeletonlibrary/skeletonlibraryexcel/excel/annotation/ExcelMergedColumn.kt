package kr.skeletonlibrary.skeletonlibraryexcel.excel.annotation

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ExcelMergedColumn(
	val headerName: String = "",
)
