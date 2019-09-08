package `fun`.dofor.devhelper.accessbility

import `fun`.dofor.devhelper.accessbility.model.*
import `fun`.dofor.devhelper.accessbility.model.mapNodeInfo
import `fun`.dofor.devhelper.accessbility.view.TrackerViewImpl
import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.os.Build
import android.view.accessibility.AccessibilityEvent

class TrackerService : AccessibilityService(), TrackerPresenter {
    companion object {
        //        const val TAG = "DEV_HELPER"
        const val KEY_TYPE = "key_type"
    }

    private var type = 0

    override val view: TrackerView by lazy {
        TrackerViewImpl(this, this)
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        type = intent?.getIntExtra(KEY_TYPE, Type.CLOSE.code) ?: Type.CLOSE.code
        if (type == Type.OPEN.code) {
            this.view.show()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                disableSelf()
            }
            this.view.hide()
        }
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onInterrupt() {
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> updatePageInfo(event)
            AccessibilityEvent.TYPE_VIEW_CLICKED, AccessibilityEvent.TYPE_VIEW_LONG_CLICKED -> updateEventInfo(event)
        }
    }

    enum class Type(val code: Int) {
        OPEN(1), CLOSE(0)
    }

    override fun loadNodeInfo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // 获取当前窗口的控件id
            this.view.showNodeInfo(NodeInfoIterator(rootInActiveWindow))
        } else {
            this.view.showNodeInfo(NodeInfoIterator(null))
        }
    }

    private fun updatePageInfo(event: AccessibilityEvent) {
        // 浮窗显示信息
        val className = event.className?.toString() ?: ""
        event.recycle()
        this.view.showPageInfo(PageInfo(className))
    }

    private fun updateEventInfo(event: AccessibilityEvent) {
        val eventClassName = event.className?.toString() ?: ""
        val source = event.source
        val eventSource = source?.mapNodeInfo()
        val info = EventInfo(eventClassName, eventSource)
        source?.recycle()
        this.view.showEventInfo(info)
    }
}