package kr.skeletonlibrary.skeletonlibraryshared.util

import java.lang.reflect.Field

object SuperClassReflectionUtil {
	fun getAllFields(clazz: Class<*>): List<Field> {
		val fields: MutableList<Field> = ArrayList()
		for (clazzInClasses in getAllClassesIncludingSuperClasses(clazz, true)) {
			fields.addAll(listOf(*clazzInClasses.declaredFields))
		}
		return fields
	}

	@JvmStatic
	@Throws(NoSuchFieldException::class)
	fun getField(
		clazz: Class<*>,
		name: String,
	): Field {
		for (clazzInClasses in getAllClassesIncludingSuperClasses(clazz, false)) {
			for (field in clazzInClasses.declaredFields) {
				if (field.name == name) {
					return clazzInClasses.getDeclaredField(name)
				}
			}
		}
		throw NoSuchFieldException()
	}

	private fun getAllClassesIncludingSuperClasses(
		clazz: Class<*>,
		fromSuper: Boolean,
	): List<Class<*>> {
		var clazz: Class<*>? = clazz
		val classes: MutableList<Class<*>> = ArrayList()
		while (clazz != null) {
			classes.add(clazz)
			clazz = clazz.superclass
		}
		if (fromSuper) {
			classes.reverse()
		}
		return classes
	}

	fun <T : Annotation?> getAnnotationOrThrow(
		field: Field,
		annotationClass: Class<T>,
	): T =
		field.getAnnotation(annotationClass)
			?: throw NoSuchElementException("Field ${field.name} has no annotation ${annotationClass.name}")
}
