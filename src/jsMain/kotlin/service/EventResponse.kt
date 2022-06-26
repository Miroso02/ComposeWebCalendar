package service

import Event
import kotlinx.serialization.Serializable
import kotlin.js.Date

@Serializable
class EventResponse(
    val id: Int = -1,
    val date: Long,
    val name: String,
    val description: String
)

fun EventResponse.toEvent() = Event(
    date = Date(date),
    name = name,
    description = description
)

fun Event.toEventResponse() = EventResponse(
    id = -1,
    date = date.getTime().toLong(),
    name = name,
    description = description
)