package service

interface EventService {
    suspend fun getEvents(): List<EventResponse>
    suspend fun addEvent(event: EventResponse)
}