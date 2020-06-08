package `fun`.dofor.devhelper

import `fun`.dofor.devhelper.tracker.Tracker
import `fun`.dofor.devhelper.uri.UriToolsActivity
import android.app.Activity
import android.content.Intent

object DevHelper {

    fun createTracker(activity: Activity) = Tracker(activity)

    fun startUriToolsActivity(activity: Activity) {
        activity.startActivity(Intent(activity, UriToolsActivity::class.java))
    }
}