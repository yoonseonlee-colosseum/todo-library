package kr.skeletonlibrary.skeletonlibraryexcel.excel.annotation

import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ExcelHeaderStyle(
	val fillForegroundColor: IndexedColors = IndexedColors.GREY_25_PERCENT,
	val alignment: HorizontalAlignment = HorizontalAlignment.CENTER,
	val isBorder: Boolean = true,
)
