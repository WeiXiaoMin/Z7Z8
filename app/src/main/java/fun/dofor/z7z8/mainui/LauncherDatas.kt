package `fun`.dofor.z7z8.mainui

import `fun`.dofor.common.util.getActivity
import `fun`.dofor.z7z8.action.CheckScreenInfo
import android.view.View
import java.io.Serializable

const val VIEW_TYPE_DEFAULT = 0
const val VIEW_TYPE_TRACKER = 1
const val VIEW_TYPE_URI_TOOLS = 2

fun createLauncherData() = arrayOf(
    TrackerLauncherData(),
    UriToolsLauncherData(),
    DefaultLauncherData("查看屏幕信息", "点击查看",
        View.OnClickListener { view -> view.getActivity()?.let { CheckScreenInfo(it).run() } })
)

interface LauncherData : Serializable {
    val viewType: Int
}

data class DefaultLauncherData(
    val title: String,
    val desc: String = "",
    var onClickListener: View.OnClickListener? = null
) : LauncherData, Serializable {
    override val viewType: Int
        get() = VIEW_TYPE_DEFAULT
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

