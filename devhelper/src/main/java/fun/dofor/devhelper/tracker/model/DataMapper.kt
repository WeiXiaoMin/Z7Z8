package `fun`.dofor.devhelper.tracker.model

import android.graphics.Rect
import android.os.Build
import android.view.accessibility.AccessibilityNodeInfo

internal fun AccessibilityNodeInfo.mapNodeInfo(): NodeInfo {
    val className = className?.toString() ?: ""
    val idName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        viewIdResourceName ?: ""
    } else {
        ""
    }
    val rect =  Rect()
    getBoundsInScreen(rect)
    return NodeInfo(className, idName, rect)
}