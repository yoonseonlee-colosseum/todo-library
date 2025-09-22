package kr.skeletonlibrary.skeletonlibraryexcel.excel.style.applystrategy

import kr.skeletonlibrary.skeletonlibraryexcel.excel.annotation.ExcelBodyStyle
import kr.skeletonlibrary.skeletonlibraryexcel.excel.style.ExcelBodyStyleGenerator
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.usermodel.Workbook

class DefaultExcelBodyStyle<T> : ExcelBodyStyleGenerator<T> {
	override fun bodyCellStyle(
		clazz: Class<T>,
		workbook: Workbook,
	): CellStyle {
		if (!clazz.isAnnotationPresent(ExcelBodyStyle::class.java)) {
			throw NoSuchElementException("Annotation @ExcelBodyStyle not found on class " + clazz.name)
		}
		val style: CellStyle = workbook.createCellStyle()

		val annotation: ExcelBodyStyle = clazz.getAnnotation(ExcelBodyStyle::class.java)
		if (annotation.isBorder) {
			style.borderTop = BorderStyle.THIN
			style.borderBottom = BorderStyle.THIN
			style.borderLeft = BorderStyle.THIN
			style.borderRight = BorderStyle.THIN
		}

		style.fillPattern = FillPatternType.NO_FILL
		style.alignment = annotation.alignment
		style.verticalAlignment = VerticalAlignment.CENTER

		return style
	}
}
