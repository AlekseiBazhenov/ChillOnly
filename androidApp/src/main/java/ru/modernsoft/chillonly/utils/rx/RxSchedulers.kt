package ru.modernsoft.chillonly.utils.rx

import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * @author e.matsyuk
 */
class RxSchedulers : RxSchedulersAbs() {
    override val mainThreadScheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
    override val ioScheduler: Scheduler
        get() = Schedulers.io()
    override val computationScheduler: Scheduler
        get() = Schedulers.computation()
}
