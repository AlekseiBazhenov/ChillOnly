package ru.modernsoft.chillonly.utils.rx

import rx.Scheduler
import rx.schedulers.Schedulers

/**
 * @author e.matsyuk
 */
class RxSchedulersTest : RxSchedulersAbs() {

    override val mainThreadScheduler: Scheduler
        get() = Schedulers.immediate()

    override val ioScheduler: Scheduler
        get() = Schedulers.immediate()

    override val computationScheduler: Scheduler
        get() = Schedulers.immediate()

}
