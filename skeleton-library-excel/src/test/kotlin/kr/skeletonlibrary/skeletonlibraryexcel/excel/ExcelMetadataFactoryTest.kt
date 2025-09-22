package kr.skeletonlibrary.skeletonlibraryexcel.excel

import kr.skeletonlibrary.skeletonlibraryexcel.excel.annotation.ExcelColumn
import kr.skeletonlibrary.skeletonlibraryexcel.excel.annotation.ExcelFileName
import kr.skeletonlibrary.skeletonlibraryexcel.excel.annotation.ExcelMergedColumn
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test

class ExcelMetadataFactoryTest {
	@Test
	@DisplayName("generateMetadata 함수가 엑셀 데이터 클래스가 주어졌을 때 ExcelMetadata 객체를 반환한다.")
	fun generateMetadataTest() {
		// given
		val clazz = TestExcelData::class.java

		// when
		val metadata = ExcelMetadataFactory.generateMetadata(clazz)

		// then
		assertThat(metadata.excelFileName)
			.contains(EXCEL_FILE_NAME)
		assertThat(metadata.excelHeaderName)
			.hasSize(3)
			.containsEntry("goodsNameKr", "상품명(한국어)")
			.containsEntry("goodsNameEng", "상품명(영문)")
			.containsEntry("goodsCode", "상품코드")
		assertThat(metadata.parentHeaderNames)
			.hasSize(3)
			.containsExactly("상품명", "상품명", null)
		assertThat(metadata.dataFieldInfo)
			.hasSize(3)
			.containsEntry("goodsNameKr", FieldInfo(dataFieldName = "상품명(한국어)", dataFieldWidth = 4000))
			.containsEntry("goodsNameEng", FieldInfo(dataFieldName = "상품명(영문)", dataFieldWidth = 4000))
			.containsEntry("goodsCode", FieldInfo(dataFieldName = "상품코드", dataFieldWidth = 4000))
	}

	@Test
	@DisplayName("getFileName 함수가 ExcelFileName 어노테이션 부재인 엑셀 데이터 클래스가 주어졌을 떄 예외가 발생한다.")
	fun getFileNameFailTest() {
		// given
		val clazz = TestFailExcelData::class.java

		// when && then
		Assertions
			.assertThatThrownBy { ExcelMetadataFactory.generateMetadata(clazz) }
			.isInstanceOf(NoSuchElementException::class.java)
	}

	@Test
	@DisplayName("getFileName 함수가 ExcelFileName 어노테이션 fileName 이 공백인 엑셀 데이터 클래스가 주어졌을 때 클래스명을 파일명으로 반환한다.")
	fun getFileNameEmptyTest() {
		// given
		val clazz = TestFileNameEmptyExcelData::class.java

		// when
		val metadata = ExcelMetadataFactory.generateMetadata(clazz)

		// then
		assertThat(metadata.excelFileName)
			.contains(TestFileNameEmptyExcelData::class.java.simpleName)
	}

	companion object {
		const val EXCEL_FILE_NAME = "엑셀파일명"
	}
}

@ExcelFileName(ExcelMetadataFactoryTest.EXCEL_FILE_NAME)
private class TestExcelData(

	@ExcelMergedColumn(headerName = "상품명")
	@ExcelColumn(headerName = "상품명(한국어)")
	private val goodsNameKr: String,

	@ExcelMergedColumn(headerName = "상품명")
	@ExcelColumn(headerName = "상품명(영문)")
	private val goodsNameEng: String,

	@ExcelColumn(headerName = "상품코드")
	private val goodsCode: String,
)

private class TestFailExcelData

@ExcelFileName("")
private class TestFileNameEmptyExcelData
