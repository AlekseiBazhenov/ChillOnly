package ru.modernsoft.chillonly.business.events

class EventSender {

    fun send(eventType: EventTypes) {
        val map = HashMap<String, Any>()
        map[EVENT_TYPE] = eventType
        RxEventBus.INSTANCE.sendEvent(map)
    }

    fun send(eventType: EventTypes, value: Any) {
        val map = HashMap<String, Any>()
        map[EVENT_TYPE] = eventType
        map[VALUE_IN_EVENT] = value
        RxEventBus.INSTANCE.sendEvent(map)
    }

    companion object {
        const val EVENT_TYPE = "event"
        const val VALUE_IN_EVENT = "value"
    }
}
