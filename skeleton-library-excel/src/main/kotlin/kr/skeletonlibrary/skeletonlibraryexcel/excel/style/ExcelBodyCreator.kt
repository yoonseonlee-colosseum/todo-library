package kr.skeletonlibrary.skeletonlibraryexcel.excel.style

import kr.skeletonlibrary.skeletonlibraryexcel.excel.ExcelMetadata
import kr.skeletonlibrary.skeletonlibraryshared.util.SuperClassReflectionUtil
import org.apache.commons.lang3.ObjectUtils
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Sheet
import java.lang.reflect.Field

class ExcelBodyCreator<T>(
	private val sheet: Sheet,
	private val metadata: ExcelMetadata,
	private val styles: ExcelStyles,
) {
	fun createBody(
		dataList: List<T>,
		rowStartIndex: Int,
	): Int {
		var rowIndex = rowStartIndex + 1
		for (data in dataList) {
			val row = sheet.createRow(rowIndex++)
			var columnIndex = COLUMN_START_INDEX
			for (dataFieldName in metadata.dataFieldInfo.keys) {
				val cell = row.createCell(columnIndex)
				val field = getField(data, dataFieldName)
				val cellValue = findCellValue(data, field)
				setCellValue(cell, cellValue, dataFieldName, columnIndex)
				columnIndex++
			}
		}
		return rowIndex
	}

	private fun setCellValue(
		cell: Cell,
		cellValue: Any?,
		dataFieldName: String,
		columnIndex: Int,
	) {
		setCellWidth(dataFieldName, columnIndex)
		if (cellValue is Number) {
			cell.setCellValue(cellValue.toDouble())
			return
		}
		cell.setCellValue(getOrEmpty(cellValue))
		cell.cellStyle = styles.bodyCellStyle
	}

	private fun setCellWidth(
		dataFieldName: String,
		columnIndex: Int,
	) {
		val fieldInfo = metadata.dataFieldInfo[dataFieldName]
		sheet.setColumnWidth(columnIndex, fieldInfo!!.dataFieldWidth)
	}

	private fun getOrEmpty(cellValue: Any?): String {
		if (ObjectUtils.isEmpty(cellValue)) {
			return ""
		}
		return cellValue.toString()
	}

	private fun findCellValue(
		data: T,
		field: Field,
	): Any? {
		field.isAccessible = true
		val cellValue = field[data]
		field.isAccessible = false
		return cellValue
	}

	private fun getField(
		data: T,
		dataFieldName: String,
	): Field = SuperClassReflectionUtil.getField(data!!::class.java, dataFieldName)

	companion object {
		const val COLUMN_START_INDEX = 0
	}
}
