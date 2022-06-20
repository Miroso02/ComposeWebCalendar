import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import kotlin.js.Date

private const val maxWeeks = 6

@Composable
fun Days(days: List<Date>, events: List<Event>, onDayClicked: (Date) -> Unit) {
    val firstDays = days.takeWhile { it.getDay() != 0 }
    val rest = days.drop(firstDays.size).chunked(7)
    val weekDays = listOf(firstDays) + rest

    (0 until maxWeeks).forEach { weekNumber ->
        Div(attrs = {
            style {
                width(100.percent)
            }
        }) {
            (0..6).forEach { dayNumber ->
                weekDays.getOrNull(weekNumber)?.firstOrNull { it.getDay() == dayNumber }.let { date ->
                    DayButton(
                        date = date,
                        events = events.filter { it.date.toDateString() == date?.toDateString() },
                        onDayClicked = onDayClicked
                    )
                }
            }
        }
    }
}

@Composable
fun DayButton(date: Date?, events: List<Event>, onDayClicked: (Date) -> Unit) {
    if (date != null) {
        Div(attrs = {
            onClick { onDayClicked(date) }
            style {
                border {
                    style = LineStyle.Solid
                    color = if (events.isEmpty()) Color.black else Color.yellow
                    width = 2.px
                }
                width((100 / 7).percent)
                height(40.px)
                display(DisplayStyle.InlineBlock)
            }
        }) {
            Text(date.getDate().toString())
        }
    } else {
        Div(attrs = {
            style {
                border {
                    style = LineStyle.Solid
                    color = Color.white
                    width = 2.px
                }
                width((100 / 7).percent)
                display(DisplayStyle.InlineBlock)
            }
        }) {
            Text(" ")
        }
    }
}