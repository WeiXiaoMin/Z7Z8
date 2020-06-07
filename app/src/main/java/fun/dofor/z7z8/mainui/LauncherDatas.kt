package `fun`.dofor.z7z8.mainui

import java.io.Serializable

const val VIEW_TYPE_TRACKER = 1
const val VIEW_TYPE_URI_TOOLS = 2

fun createLauncherData() = arrayOf(
    TrackerLauncherData(),
    UriToolsLauncherData()
)

interface LauncherData : Serializable {
    val viewType: Int
}

class UriToolsLauncherData : LauncherData, Serializable {
    override val viewType: Int = VIEW_TYPE_URI_TOOLS
}

data class TrackerLauncherData(
    var disableClassFilter: Boolean = false,
    var showEventInfo: Boolean = false
) : LauncherData, Serializable {
    override val viewType: Int = VIEW_TYPE_TRACKER
}

