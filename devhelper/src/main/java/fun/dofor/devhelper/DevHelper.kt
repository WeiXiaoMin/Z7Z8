package `fun`.dofor.devhelper

import `fun`.dofor.devhelper.tracker.Tracker
import android.app.Activity

object DevHelper {

    fun createTracker(activity: Activity) = Tracker(activity)

    fun startUriIntentActivity() {
        TODO("empty function")
    }
}