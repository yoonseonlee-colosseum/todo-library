package kr.skeletonlibrary.skeletonlibraryexcel.excel.style.applystrategy

import kr.skeletonlibrary.skeletonlibraryexcel.excel.annotation.ExcelHeaderStyle
import kr.skeletonlibrary.skeletonlibraryexcel.excel.style.ExcelHeaderStyleGenerator
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.usermodel.Workbook

class DefaultExcelHeaderStyle<T> : ExcelHeaderStyleGenerator<T> {
	override fun headerCellStyle(
		clazz: Class<T>,
		workbook: Workbook,
	): CellStyle {
		if (!clazz.isAnnotationPresent(ExcelHeaderStyle::class.java)) {
			throw NoSuchElementException("Annotation @ExcelHeaderStyle not found on class " + clazz.name)
		}
		val style: CellStyle = workbook.createCellStyle()

		style.setFont(createBoldFont(workbook))

		val annotation: ExcelHeaderStyle = clazz.getAnnotation(ExcelHeaderStyle::class.java)
		if (annotation.isBorder) {
			style.borderTop = BorderStyle.THIN
			style.borderBottom = BorderStyle.THIN
			style.borderLeft = BorderStyle.THIN
			style.borderRight = BorderStyle.THIN
		}

		style.fillPattern = FillPatternType.SOLID_FOREGROUND
		style.fillForegroundColor = annotation.fillForegroundColor.getIndex()
		style.alignment = annotation.alignment
		style.verticalAlignment = VerticalAlignment.CENTER

		return style
	}

	private fun createBoldFont(workbook: Workbook): Font {
		val font: Font = workbook.createFont()
		font.bold = true
		return font
	}
}
