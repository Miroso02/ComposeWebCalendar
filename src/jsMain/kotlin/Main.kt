import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.jetbrains.compose.web.renderComposable
import kotlin.js.Date

fun main() {
    var currentMonth: Date by mutableStateOf(Date())
    val events = mutableListOf<Event>()
    var selectedDay: Date? by mutableStateOf(null)

    renderComposable(rootElementId = "root") {
        Legend(
            currentMonth = currentMonth,
            onPrevMonth = {
                currentMonth = Date(currentMonth.getFullYear(), currentMonth.getMonth() - 1)
            },
            onNextMonth = {
                currentMonth = Date(currentMonth.getFullYear(), currentMonth.getMonth() + 1)
            }
        )
        Days(
            days = currentMonth.daysOfMonth,
            events = events,
            onDayClicked = { selectedDay = it }
        )
        selectedDay?.let { date ->
            DayInfo(
                date = date,
                events = events.filter { it.date.toDateString() == date.toDateString() },
                onEventAdded = { events.add(it) }
            )
        }
    }
}

val Date.daysOfMonth get() =
    generateSequence(Date(this.getFullYear(), this.getMonth(), 1)) {
        Date(this.getFullYear(), this.getMonth(), it.getDate() + 1)
    }
        .takeWhile { it.getMonth() == this.getMonth() }
        .toList()

