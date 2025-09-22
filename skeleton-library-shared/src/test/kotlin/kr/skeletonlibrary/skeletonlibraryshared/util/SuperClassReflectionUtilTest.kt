package kr.skeletonlibrary.skeletonlibraryshared.util

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test

class SuperClassReflectionUtilTest {
	@Test
	@DisplayName("getAllFields 함수에 클래스가 주어졌을때 상위클래스의 모든 필드 정보를 담은 결과를 반환한다.")
	fun getAllFieldsTest() {
		// given
		val clazz = SubClass::class.java

		// when
		val allFields = SuperClassReflectionUtil.getAllFields(clazz)

		// then
		assertThat(allFields).hasSize(4)
		assertThat(allFields.map { it.name })
			.containsExactlyInAnyOrder("name", "age", "address", "type")
	}

	@Test
	@DisplayName("getFields 함수에 클래스와 올바른 필드가 주어졌을 때 클래스 필드 정보를 반환한다.")
	fun getFieldTest() {
		// given
		val clazz = SubClass::class.java
		val existFieldName = "name"

		// when
		val field = SuperClassReflectionUtil.getField(clazz, existFieldName)

		// then
		assertThat(field.name).isEqualTo("name")
		assertThat(field.declaringClass).isEqualTo(SubClass::class.java)
		assertThat(field.type).isEqualTo(String::class.java)
	}

	@Test
	@DisplayName("getFields 함수에 클래스와 존재하지않는 필드가 주어졌을 때 NoSuchFieldException 가 발생한다.")
	fun getFieldExceptionTest() {
		// given
		val clazz = SuperClass::class.java
		val noneExistFieldName = "name"

		// when && then
		assertThatThrownBy { SuperClassReflectionUtil.getField(clazz, noneExistFieldName) }
			.isInstanceOf(NoSuchFieldException::class.java)
	}

	@Test
	@DisplayName("getAnnotationOrThrow 함수에 올바른 필드와 어노테이션이 주어졌을 때 어노테이션 정보를 반환한다.")
	fun getAnnotationOrThrowTest() {
		// given
		val clazz = SubClass::class.java
		val existFieldName = "age"
		val field = SuperClassReflectionUtil.getField(clazz, existFieldName)

		// when
		val annotation = SuperClassReflectionUtil.getAnnotationOrThrow(field, TestAnnotation::class.java)

		// then
		assertThat(annotation!!.description).isEqualTo("TEST")
	}

	@Test
	@DisplayName("getAnnotationOrThrow 함수에 올바르지 않은 필드와 어노테이션이 주어졌을 때 NoSuchElementException 가 발생한다.")
	fun getAnnotationOrThrowExceptionTest() {
		// given
		val clazz = SubClass::class.java
		val existFieldName = "address"
		val field = SuperClassReflectionUtil.getField(clazz, existFieldName)

		// when && then
		assertThatThrownBy { SuperClassReflectionUtil.getAnnotationOrThrow(field, TestAnnotation::class.java) }
			.isInstanceOf(NoSuchElementException::class.java)
	}
}

private open class SuperClass(
	private val type: String = "클래스",
)

private class SubClass(

	private val name: String = "이름",

	@TestAnnotation(description = "TEST")
	private val age: Int = 1,

	private val address: String = "사조빌딩",
) : SuperClass()

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
private annotation class TestAnnotation(
	val description: String,
)
