package kr.skeletonlibrary.skeletonlibraryexcel.excel.style

import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.xssf.streaming.SXSSFWorkbook

object ExcelStylesFactory {
	fun <T> create(
		type: Class<T>,
		workbook: SXSSFWorkbook,
		headerStyle: ExcelHeaderStyleGenerator<T>,
		bodyStyle: ExcelBodyStyleGenerator<T>,
	): ExcelStyles =
		ExcelStyles(
			headerCellStyle = headerStyle.headerCellStyle(type, workbook),
			bodyCellStyle = bodyStyle.bodyCellStyle(type, workbook),
		)
}

data class ExcelStyles(
	val headerCellStyle: CellStyle,
	val bodyCellStyle: CellStyle,
)
