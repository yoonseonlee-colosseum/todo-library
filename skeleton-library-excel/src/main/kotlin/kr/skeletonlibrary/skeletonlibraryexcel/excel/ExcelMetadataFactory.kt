package kr.skeletonlibrary.skeletonlibraryexcel.excel

import kr.skeletonlibrary.skeletonlibraryexcel.excel.annotation.ExcelColumn
import kr.skeletonlibrary.skeletonlibraryexcel.excel.annotation.ExcelFileName
import kr.skeletonlibrary.skeletonlibraryexcel.excel.annotation.ExcelMergedColumn
import kr.skeletonlibrary.skeletonlibraryshared.util.FilenameUtil
import kr.skeletonlibrary.skeletonlibraryshared.util.SuperClassReflectionUtil
import org.apache.commons.lang3.StringUtils
import java.lang.reflect.Field

object ExcelMetadataFactory {
	fun generateMetadata(clazz: Class<*>): ExcelMetadata {
		val headerNamesMap: MutableMap<String, String> = LinkedHashMap()
		val parentHeaderNames: MutableList<String?> = ArrayList()
		val dataFieldInfoMap: MutableMap<String, FieldInfo> = LinkedHashMap()

		for (field in SuperClassReflectionUtil.getAllFields(clazz)) {
			if (field.isAnnotationPresent(ExcelColumn::class.java)) {
				headerNamesMap[field.name] = findHeaderName(field)
				parentHeaderNames.add(findParentHeaderName(field))
				dataFieldInfoMap[field.name] = generateFieldInfo(field)
			}
		}

		return ExcelMetadata(getFileName(clazz), headerNamesMap, parentHeaderNames.toList(), dataFieldInfoMap)
	}

	private fun findParentHeaderName(field: Field): String? {
		if (!field.isAnnotationPresent(ExcelMergedColumn::class.java)) {
			return null
		}
		return field.getAnnotation(ExcelMergedColumn::class.java).headerName
	}

	private fun findHeaderName(field: Field): String {
		val headerName: String = field.getAnnotation(ExcelColumn::class.java).headerName

		if (StringUtils.isNotBlank(headerName)) {
			return headerName
		}
		return field.name
	}

	private fun generateFieldInfo(field: Field): FieldInfo {
		val headerName = field.getAnnotation(ExcelColumn::class.java).headerName
		val width = field.getAnnotation(ExcelColumn::class.java).width
		return FieldInfo(headerName, width)
	}

	private fun getFileName(clazz: Class<*>): String {
		// 엑셀 data class 에 ExcelFileName 어노테이션 부재 시 예외
		if (!clazz.isAnnotationPresent(ExcelFileName::class.java)) {
			throw NoSuchElementException("Annotation @ExcelFileName not found on class " + clazz.name)
		}
		return FilenameUtil.appendSuffixWithDate(findFileName(clazz))
	}

	private fun findFileName(type: Class<*>): String {
		val fileName: String = type.getAnnotation(ExcelFileName::class.java).fileName
		if (!StringUtils.isNotBlank(fileName)) {
			return type.simpleName
		}
		return fileName
	}
}
