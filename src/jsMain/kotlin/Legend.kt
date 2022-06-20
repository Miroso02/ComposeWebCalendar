import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import kotlin.js.Date

private val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
private val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec")

@Composable
fun Legend(
    currentMonth: Date,
    onNextMonth: () -> Unit,
    onPrevMonth: () -> Unit
) {
    val width = 100 / daysOfWeek.size
    Div(
        attrs = {
            style {
                width(100.percent)
                textAlign("center")
                justifyContent(JustifyContent.SpaceBetween)
                display(DisplayStyle.Flex)
            }
        }
    ) {
        Button(attrs = {
            onClick { onPrevMonth() }
            style {
                display(DisplayStyle.InlineBlock)
                width(5.percent)
            }
        }) {
            Text("-")
        }
        H2(attrs = {
            style {
                display(DisplayStyle.InlineBlock)
            }
        }) {
            Text("${months[currentMonth.getMonth()]}, ${currentMonth.getFullYear()}")
        }
        Button(attrs = {
            onClick { onNextMonth() }
            style {
                display(DisplayStyle.InlineBlock)
                width(5.percent)
            }
        }) {
            Text("+")
        }
    }
    daysOfWeek.forEach {
        Div(attrs = {
            style {
                display(DisplayStyle.InlineBlock)
                width(width.percent)
                justifyContent(JustifyContent.SpaceBetween)
                textAlign("center")
            }
        }) {
            H4 {
                Text(it)
            }
        }
    }
}