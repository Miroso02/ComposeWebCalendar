package service

import service.api.ApiClient

class EventServiceImpl(private val client: ApiClient) : EventService {

    companion object {
        private const val PATH_GET_EVENTS = "events"
        private const val PATH_ADD_EVENT = "event/add"
    }

    override suspend fun getEvents(): List<EventResponse> =
        client.get(PATH_GET_EVENTS)

    override suspend fun addEvent(event: EventResponse) {
        client.post(PATH_ADD_EVENT, event)
    }
}