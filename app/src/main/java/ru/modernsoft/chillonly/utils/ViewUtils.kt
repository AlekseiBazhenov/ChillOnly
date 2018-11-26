package ru.modernsoft.chillonly.utils

import android.content.res.Configuration
import androidx.fragment.app.FragmentActivity

object ViewUtils {

    fun orientation(activity: FragmentActivity?): Int {
        if (activity != null) {
            return activity.resources.configuration.orientation
        }
        return Configuration.ORIENTATION_PORTRAIT
    }
}