package ru.modernsoft.chillonly.utils.rx

import rx.Observable
import rx.Scheduler
import rx.Single

/**
 * Utils class that provide Rx Schedulers and appropriate Rx Transformers

 * @author e.matsyuk
 */
abstract class RxSchedulersAbs {

    abstract val mainThreadScheduler: Scheduler
    abstract val ioScheduler: Scheduler
    abstract val computationScheduler: Scheduler

    fun <T> getIOToMainTransformer(): Observable.Transformer<T, T> {
        return Observable.Transformer<T, T> { objectObservable ->
            objectObservable
                    .subscribeOn(ioScheduler)
                    .observeOn(mainThreadScheduler)
        }
    }

    fun <T> getIOToMainTransformerSingle(): Single.Transformer<T, T> {
        return Single.Transformer<T, T> { objectObservable ->
            objectObservable
                    .subscribeOn(ioScheduler)
                    .observeOn(mainThreadScheduler)
        }
    }

    fun <T> getComputationToMainTransformer(): Observable.Transformer<T, T> {
        return Observable.Transformer<T, T> { objectObservable ->
            objectObservable
                    .subscribeOn(computationScheduler)
                    .observeOn(mainThreadScheduler)
        }
    }

    fun <T> getComputationToMainTransformerSingle(): Single.Transformer<T, T> {
        return Single.Transformer<T, T> { objectObservable ->
            objectObservable
                    .subscribeOn(computationScheduler)
                    .observeOn(mainThreadScheduler)
        }
    }

}
