import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.*
import kotlin.js.Date

@Composable
fun DayInfo(date: Date, events: List<Event>, onEventAdded: (Event) -> Unit) {
    H3 {
        Text(date.toDateString())
    }
    if (events.isEmpty()) {
        H4 {
            Text("No events planned")
        }
    } else {
        H4 {
            Text("Events:")
        }
        Table(attrs = {
            style {
                width(50.percent)
                textAlign("center")
            }
        }) {
            Tr {
                Th { Text("Name") }
                Th { Text("Description") }
            }
            events.forEach {
                Tr {
                    Td { Text(it.name) }
                    Td { Text(it.description) }
                }
            }
        }
    }

    var eventName by mutableStateOf("")
    var eventDescription by mutableStateOf("")

    Span {
        Input(InputType.Text) {
            placeholder("Name")
            value(eventName)
            onInput { eventName = it.value }
        }
        Input(InputType.Text) {
            placeholder("Description")
            value(eventDescription)
            onInput { eventDescription = it.value }
        }
        Button(attrs = {
            onClick {
                onEventAdded(Event(date, eventName, eventDescription))
            }
        }) {
            Text("Add event")
        }
    }
}