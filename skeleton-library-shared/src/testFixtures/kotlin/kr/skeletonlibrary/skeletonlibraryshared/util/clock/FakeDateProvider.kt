package kr.skeletonlibrary.skeletonlibraryshared.util.clock

import java.time.LocalDate

class FakeDateProvider : DateProvider {
	override fun nowDate(): LocalDate = LocalDate.of(9999, 12, 31)
}
