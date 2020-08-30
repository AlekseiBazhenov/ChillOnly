package ru.modernsoft.chillonly

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber
import timber.log.Timber.DebugTree

class ChillApp : Application() {

    // todo Moxy, Dagger, krealmextensions, tests, ProGuard

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config)

        //        leakCanary();
    }

    //    private void leakCanary() {
    //        if (LeakCanary.isInAnalyzerProcess(this)) {
    //            return;
    //        }
    //        LeakCanary.install(this);
    //    }
}
