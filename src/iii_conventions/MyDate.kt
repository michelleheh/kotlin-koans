package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        when {
            year > other.year -> return 1
            year < other.year -> return -1
            year == other.year -> {
                when {
                    month > other.month -> return 1
                    month < other.month -> return -1
                    month == other.month -> {
                        when {
                            dayOfMonth > other.dayOfMonth -> return 1
                            dayOfMonth < other.dayOfMonth -> return -1
                        }
                    }

                }
            }
        }
        return 0
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange {
    return DateRange(this, other)
}

operator fun MyDate.plus(repeatedTimeInterval: RepeatedTimeInterval): MyDate {
    return this.addTimeIntervals(repeatedTimeInterval.timeInterval, repeatedTimeInterval.int)
}

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate {
    return this.addTimeIntervals(timeInterval, 1)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class RepeatedTimeInterval(val int: Int, val timeInterval: TimeInterval)

operator fun TimeInterval.times(int: Int): RepeatedTimeInterval {
    return RepeatedTimeInterval(int, this)
}

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
