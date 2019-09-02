package `fun`.dofor.devhelper.accessbility

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent

class TrackerService : AccessibilityService() {
    companion object {
        const val  TAG = "DEV_HELPER"
        const val  KEY_TYPE ="key_type"
        var filterClassName = false
    }

    private var type =  0

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        type = intent?.getIntExtra(KEY_TYPE, Type.CLOSE.code) ?: Type.CLOSE.code
        if (type == Type.OPEN.code) {
            TODO()
        } else {
            TODO()
        }
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onInterrupt() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    enum class Type(val code: Int) {
        OPEN(1), CLOSE(0)
    }
}