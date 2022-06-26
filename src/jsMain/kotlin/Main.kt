import androidx.compose.runtime.*
import kotlinx.browser.window
import kotlinx.coroutines.flow.collect
import org.jetbrains.compose.web.renderComposable
import service.EventService
import service.EventServiceImpl
import service.api.ApiClient
import kotlin.js.Date

fun main() {
    val eventService: EventService = EventServiceImpl(ApiClient)
    val viewModel = MainViewModel(eventService)
    var currentMonth: Date by mutableStateOf(Date())
    var selectedDay: Date? by mutableStateOf(null)

    renderComposable(rootElementId = "root") {
        val scope = rememberCoroutineScope()
        val events by viewModel.events.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.errorEvent.collect {
                window.alert(it.stackTraceToString())
            }
        }

        LaunchedEffect(Unit) {
            viewModel.getEvents(scope)
        }

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
                onEventAdded = { viewModel.addEvent(scope, it) }
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

