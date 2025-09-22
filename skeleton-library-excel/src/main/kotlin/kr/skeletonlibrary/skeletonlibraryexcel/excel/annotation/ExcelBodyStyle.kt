package kr.skeletonlibrary.skeletonlibraryexcel.excel.annotation

import org.apache.poi.ss.usermodel.HorizontalAlignment

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ExcelBodyStyle(
	val isBorder: Boolean = true,
	val alignment: HorizontalAlignment = HorizontalAlignment.LEFT,
)
