package `fun`.dofor.devhelper.tracker.model

import android.graphics.Rect

data class PageInfo(
    val className: String
)

data class EventInfo(
    val className: String,
    val source: NodeInfo?
)

data class NodeInfo(
    val className: String,
    val viewIdResourceName: String,
    val boundsInScreen: Rect
)