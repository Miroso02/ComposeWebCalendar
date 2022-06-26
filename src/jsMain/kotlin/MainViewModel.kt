import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import service.EventResponse
import service.EventService
import service.toEvent
import service.toEventResponse

class MainViewModel(private val service: EventService) {

    private val _events = MutableStateFlow<List<Event>>(listOf())
    val events: StateFlow<List<Event>> = _events

    private val _errorEvent = MutableSharedFlow<Throwable>()
    val errorEvent: SharedFlow<Throwable> = _errorEvent

    fun getEvents(scope: CoroutineScope) = scope.launch {
        runCatching { service.getEvents().map(EventResponse::toEvent) }
            .onSuccess { _events.emit(it) }
            .onFailure {
                _errorEvent.emit(it)
            }
    }

    fun addEvent(scope: CoroutineScope, event: Event) = scope.launch {
        runCatching { service.addEvent(event.toEventResponse()) }
            .onFailure { _errorEvent.emit(it) }
            .onSuccess { getEvents(scope) }
    }
}