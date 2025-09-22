package kr.skeletonlibrary.skeletonlibraryexcel.excel.style

import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.Workbook

interface ExcelHeaderStyleGenerator<T> {
	fun headerCellStyle(
		type: Class<T>,
		workbook: Workbook,
	): CellStyle
}
