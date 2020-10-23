package ru.modernsoft.chillonly

import android.app.Application
import android.os.StrictMode
import timber.log.Timber
import timber.log.Timber.DebugTree

class ChillApp : Application() {

    // todo DI(Hilt, Koin), tests, ProGuard

    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
        }
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
