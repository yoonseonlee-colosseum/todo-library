package kr.skeletonlibrary.skeletonlibraryshared.util.clock

import java.time.LocalDate

interface DateProvider {
	fun nowDate(): LocalDate
}
