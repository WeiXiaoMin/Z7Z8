package `fun`.dofor.devhelper.tracker

import `fun`.dofor.common.util.*
import android.app.Activity
import android.content.Intent
import android.os.Build

class Tracker(private val activity: Activity) {

    /**
     * 关闭类名过滤
     */
    var disableClassFilter by DisableClassFilterDelegate
    /**
     * 显示事件信息
     */
    var showEventInfo by ShowEventInfoDelegate

    fun start(requestCode: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            activity.toast("Android 4.3 以上版本才能体验完整功能。")
        }

        if (!activity.isOverlayPermissionGranted()) {
            activity.toast("当前无悬浮窗权限，请先授权")
            activity.requestOverlayPermission(requestCode)
            return
        }
        if (!activity.isAccessibilityEnable()) {
            activity.toast("请先开启【${activity.getAppName()}】的辅助功能")
            activity.requestAccessibility(requestCode)
            return
        }
        val intent = Intent(activity, TrackerService::class.java)
        intent.putExtra(TrackerService.KEY_TYPE, TrackerService.Type.OPEN.code)
        activity.startService(intent)
    }

    fun close() {
        val intent = Intent(activity, TrackerService::class.java)
        intent.putExtra(TrackerService.KEY_TYPE, TrackerService.Type.CLOSE.code)
        activity.startService(intent)
    }
}