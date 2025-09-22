package kr.skeletonlibrary.skeletonlibraryexcel.excel.style

import kr.skeletonlibrary.skeletonlibraryexcel.excel.ExcelMetadata
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.util.CellRangeAddress

class ExcelHeaderCreator(
	private val sheet: Sheet,
	private val metadata: ExcelMetadata,
	private val styles: ExcelStyles,
) {
	fun createHeader(): Int {
		val rowIndex = ROW_START_INDEX
		if (metadata.isIncludeParentHeader()) {
			return createMultiHeader(rowIndex)
		}
		return createSingleHeader(rowIndex)
	}

	private fun createSingleHeader(rowStartIndex: Int): Int {
		val row = sheet.createRow(rowStartIndex)
		var columnIndex = COLUMN_START_INDEX
		for (dataFieldName in metadata.dataFieldInfo.keys) {
			val cell = row.createCell(columnIndex++)
			val value = metadata.getExcelHeaderName(dataFieldName)
			cell.setCellValue(value)
			cell.cellStyle = styles.headerCellStyle
		}
		return rowStartIndex
	}

	private fun createMultiHeader(rowIndex: Int): Int {
		var currentRowIndex = rowIndex
		mergeHeaderCell(currentRowIndex)
		val parentHeader = createParentHeader(currentRowIndex++)
		val row = sheet.createRow(currentRowIndex)
		var columnIndex = COLUMN_START_INDEX
		for (dataFieldName in metadata.dataFieldInfo.keys) {
			val value = metadata.getExcelHeaderName(dataFieldName)
			if (metadata.parentHeaderNames[columnIndex] == null) {
				val parentHeaderCell = parentHeader[columnIndex]
				parentHeaderCell!!.setCellValue(value)
				parentHeaderCell.cellStyle = styles.headerCellStyle
			}
			val cell = row.createCell(columnIndex++)
			cell.setCellValue(value)
			cell.cellStyle = styles.headerCellStyle
		}
		return currentRowIndex
	}

	private fun mergeHeaderCell(rowIndex: Int) {
		mergeParentHeadersHorizontallyByGroup(rowIndex)
		mergeParentHeadersVertically()
	}

	private fun createParentHeader(rowIndex: Int): Map<Int, Cell> {
		val parentHeaderCellMap: MutableMap<Int, Cell> = LinkedHashMap()
		val row = sheet.createRow(rowIndex)
		var columnIndex = 0
		for (parent in metadata.parentHeaderNames) {
			val cell = row.createCell(columnIndex)
			cell.setCellValue(parent)
			cell.cellStyle = styles.headerCellStyle
			parentHeaderCellMap[columnIndex] = cell
			columnIndex++
		}
		return parentHeaderCellMap
	}

	/**
	 * 상위 헤더의 값이 null일 경우, 해당 셀과 하위 셀을 병합(세로 병합)
	 */
	private fun mergeParentHeadersVertically() {
		for (i in metadata.parentHeaderNames.indices) {
			if (metadata.parentHeaderNames[i] == null) {
				sheet.addMergedRegion(CellRangeAddress(0, 1, i, i))
			}
		}
	}

	/**
	 * 상위 헤더 값이 동일한 구간을 찾아 가로로 병합
	 */
	private fun mergeParentHeadersHorizontallyByGroup(rowIndex: Int) {
		var startIndex = -1
		var currentValue: String? = null
		val parentHeaderNames = metadata.parentHeaderNames
		for (i in parentHeaderNames.indices) {
			val value = parentHeaderNames[i]
			if (currentValue != value) {
				if (currentValue != null && startIndex != -1) {
					mergeParentHeader(rowIndex, startIndex, i - 1)
				}
				startIndex = if (value == null) -1 else i
				currentValue = value
			}
		}
		if (currentValue != null && startIndex != -1) {
			mergeParentHeader(rowIndex, startIndex, parentHeaderNames.size - 1)
		}
	}

	private fun mergeParentHeader(
		rowIndex: Int,
		startIndex: Int,
		endIndex: Int,
	) {
		if (startIndex <= endIndex) {
			sheet.addMergedRegion(CellRangeAddress(rowIndex, rowIndex, startIndex, endIndex))
		}
	}

	companion object {
		const val COLUMN_START_INDEX = 0
		const val ROW_START_INDEX = 0
	}
}
