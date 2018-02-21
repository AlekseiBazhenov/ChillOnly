package ru.modernsoft.chillonly.business.events

import java.util.HashMap

import rx.subjects.PublishSubject

class RxEventBus private constructor() {

    val events = PublishSubject.create<HashMap<String, Any>>()

    fun sendEvent(data: HashMap<String, Any>) {
        events.onNext(data)
    }

    companion object {

        private var instance: RxEventBus? = null

        val INSTANCE: RxEventBus
            get() {
                if (instance == null) instance = RxEventBus()
                return instance as RxEventBus
            }
    }
}
