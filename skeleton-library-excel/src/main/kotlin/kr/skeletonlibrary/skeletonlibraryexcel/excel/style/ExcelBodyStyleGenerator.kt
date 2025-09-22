package kr.skeletonlibrary.skeletonlibraryexcel.excel.style

import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.Workbook

interface ExcelBodyStyleGenerator<T> {
	fun bodyCellStyle(
		type: Class<T>,
		workbook: Workbook,
	): CellStyle
}
