package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate) = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate) = DateRange(this, other)

operator fun MyDate.plus(repeatedTimeInterval: RepeatedTimeInterval)
        = this.addTimeIntervals(repeatedTimeInterval.timeInterval, repeatedTimeInterval.int)
operator fun MyDate.plus(timeInterval: TimeInterval)
        = this.addTimeIntervals(timeInterval, 1)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class RepeatedTimeInterval(val int: Int, val timeInterval: TimeInterval)

operator fun TimeInterval.times(int: Int) = RepeatedTimeInterval(int, this)

class DateRange(
        override val start: MyDate,
        override val endInclusive: MyDate
) : ClosedRange<MyDate>, Iterator<MyDate> {

    private  var current = start

    override fun hasNext(): Boolean {
        return current <= endInclusive
    }

    override fun next(): MyDate {
        val result = current
        current = current.nextDay()
        return result
    }
}
