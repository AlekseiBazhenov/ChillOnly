package ru.modernsoft.chillonly

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

class ChillApp : Application() {

    // todo DI(Hilt, Koin), tests, ProGuard

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        //        leakCanary();
    }

    //    private void leakCanary() {
    //        if (LeakCanary.isInAnalyzerProcess(this)) {
    //            return;
    //        }
    //        LeakCanary.install(this);
    //    }
}
